package com.flab.sooldama.domain.user.dao;

import com.flab.sooldama.domain.user.domain.User;
import java.time.LocalDateTime;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class UserMapperTest {

	@Autowired
	private UserMapper userMapper;

	@BeforeEach
	public void setUp() {
		userMapper.deleteAllUsers();
	}

	@Test
	@DisplayName("필수 정보 넣지 않을 경우 insertUser 실패")
	public void failInsertUser() {
		User user = User.builder()
			.email("sehoon@fmail.com")
			.name("sehoon gim")
			.phoneNumber("010-1010-1010")
			.nickname("sesoon")
			.isAdult(true)
			.build();

		org.junit.jupiter.api.Assertions.assertThrows(DataIntegrityViolationException.class,
			() -> userMapper.insertUser(user));
	}

	@Test
	@DisplayName("id로 사용자 검색 시 사용자가 없을 경우 Optional.empty 반환")
	public void findNonExistsUserById() {
		Long wrongId = -1L;

		Assertions.assertThat(userMapper.findUserById(wrongId)).isEmpty();
	}

	@Test
	@DisplayName("email로 사용자 검색 시 사용자가 없을 경우 Optional.empty 반환")
	public void findNonExistsUserByEmail() {
		String wrongEmail = "wrong@email.com";

		Assertions.assertThat(userMapper.findUserByEmail(wrongEmail)).isEmpty();
	}

	@Test
	@DisplayName("UserMapper 클래스와 UserMapper.xml의 insertUser 반환 타입 불일치 오류 확인")
	public void insertUserReturnTypeNotMatched() {
		User user = User.builder()
			.email("sehoon@fmail.com")
			.password("abracadabra")
			.name("sehoon gim")
			.phoneNumber("010-1010-1010")
			.nickname("sesoon")
			.isAdult(true)
			.build();

		userMapper.insertUser(user);
	}
}
