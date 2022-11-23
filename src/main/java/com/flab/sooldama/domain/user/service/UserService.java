package com.flab.sooldama.domain.user.service;

import com.flab.sooldama.domain.user.dao.UserMapper;
import com.flab.sooldama.domain.user.domain.User;
import com.flab.sooldama.domain.user.dto.request.JoinUserRequest;
import com.flab.sooldama.domain.user.dto.request.LoginUserRequest;
import com.flab.sooldama.domain.user.dto.response.JoinUserResponse;
import com.flab.sooldama.domain.user.exception.NoSuchUserException;
import com.flab.sooldama.domain.user.exception.DuplicateEmailExistsException;
import com.flab.sooldama.domain.user.exception.PasswordNotMatchException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// @Service 어노테이션은 핵심 비즈니스 로직을 담은 서비스 클래스를 빈으로 등록시켜주기 위한 어노테이션입니다.
@Service
@RequiredArgsConstructor // final 이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션입니다.
public class UserService {

	private final UserMapper userMapper;
	private static final String USER_EMAIL = "USER_EMAIL";
	private static final String ENCRYPTION_ALGORITHM = "SHA-256";

	public JoinUserResponse insertUser(JoinUserRequest request) {
		userMapper.findUserByEmail(request.getEmail()).ifPresent(user -> {
			throw new DuplicateEmailExistsException("이메일 주소 중복입니다");
		});

		String encryptedPassword = encryptPassword(request.getPassword());
		User user = JoinUserRequest.builder()
			.email(request.getEmail())
			.password(encryptedPassword)
			.name(request.getName())
			.phoneNumber(request.getPhoneNumber())
			.nickname(request.getNickname())
			.isAdult(request.isAdult())
			.build()
			.toUser();

		userMapper.insertUser(user);
		User userByEmail = userMapper.findUserByEmail(request.getEmail()).get();

		return JoinUserResponse.builder()
			.id(userByEmail.getId())
			.email(userByEmail.getEmail())
			.password(userByEmail.getPassword())
			.name(userByEmail.getName())
			.phoneNumber(userByEmail.getPhoneNumber())
			.nickname(userByEmail.getNickname())
			.isAdult(userByEmail.isAdult())
			.createdAt(userByEmail.getCreatedAt())
			.updatedAt(userByEmail.getUpdatedAt())
			.deletedAt(userByEmail.getDeletedAt())
			.build();
	}

	public JoinUserResponse findUserById(Long id) {
		User matchedUser = userMapper.findUserById(id).orElseThrow(() -> {
			throw new NoSuchUserException("사용자를 찾을 수 없습니다");
		});

		return JoinUserResponse.builder()
			.id(matchedUser.getId())
			.email(matchedUser.getEmail())
			.password(matchedUser.getPassword())
			.name(matchedUser.getName())
			.phoneNumber(matchedUser.getPhoneNumber())
			.nickname(matchedUser.getNickname())
			.isAdult(matchedUser.isAdult())
			.createdAt(matchedUser.getCreatedAt())
			.updatedAt(matchedUser.getUpdatedAt())
			.deletedAt(matchedUser.getDeletedAt())
			.build();
	}

	public void loginUser(LoginUserRequest request, HttpSession session) {
		User user = userMapper.findUserByEmail(request.getEmail()).orElseThrow(() -> {
			throw new NoSuchUserException("등록된 사용자가 아닙니다");
		});

		if (!encryptPassword(request.getPassword()).equals(user.getPassword())) {
			throw new PasswordNotMatchException("비밀번호가 다릅니다");
		}

		session.setAttribute(USER_EMAIL, request.getEmail());
	}

	private String encryptPassword(String password) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(ENCRYPTION_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("비밀번호 암호화에 실패했습니다");
		}

		digest.update(password.getBytes(StandardCharsets.UTF_8));
		byte[] hash = digest.digest();

		return formatInHex(hash);
	}

	private String formatInHex(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		for (byte b : bytes) {
			result.append(String.format("%02x", b));
		}

		return result.toString();
	}

	public void logoutUser(HttpSession session) {

	}
}
