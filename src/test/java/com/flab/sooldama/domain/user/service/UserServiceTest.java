package com.flab.sooldama.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.sooldama.domain.user.dao.UserMapper;
import com.flab.sooldama.domain.user.domain.User;
import com.flab.sooldama.domain.user.dto.request.JoinUserRequest;
import com.flab.sooldama.domain.user.dto.request.LoginUserRequest;
import com.flab.sooldama.domain.user.dto.response.JoinUserResponse;
import com.flab.sooldama.domain.user.exception.DuplicateEmailExistsException;
import com.flab.sooldama.domain.user.exception.NoSuchUserException;
import com.flab.sooldama.domain.user.exception.PasswordNotMatchException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;

/*
 * @ExtendWith 어노테이션은 테스트에서 사용할 클래스를 명시합니다. @ExtendWith(MockitoExtension.class)를 사용함으로써
 * openMocks 등의 메소드를 생략할 수 있습니다.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserMapper userMapper;

	private JoinUserRequest request;

	@BeforeEach
	public void setUp() {
		this.request = JoinUserRequest.builder()
			.email("sehoon@fmail.com")
			.password("abracadabra")
			.name("sehoon gim")
			.phoneNumber("010-1010-1010")
			.nickname("sesoon")
			.isAdult(true)
			.build();
	}

	@Test
	@DisplayName("사용자가 회원가입하면 DB에 회원정보가 추가되나")
	public void userInfoAddedOnDB() {
		// 테스트 데이터 및 동작 정의
		User user = User.builder()
			.id(1L)
			.email(this.request.getEmail())
			.password(this.request.getPassword())
			.name(this.request.getName())
			.phoneNumber(this.request.getPhoneNumber())
			.nickname(this.request.getNickname())
			.isAdult(this.request.isAdult())
			.createdAt(LocalDateTime.now())
			.build();

		doNothing().when(userMapper).insertUser(any(User.class));
		when(userMapper.findUserById(any(Long.class))).thenReturn((Optional.of(user)));
		when(userMapper.findUserByEmail(any(String.class))).thenAnswer(new Answer() {
			private int count = 0;

			public Object answer(InvocationOnMock invocation) {
				if (++count == 1) {
					return Optional.ofNullable(null);
				} else {
					return Optional.of(user);
				}
			}
		});

		// 실행
		JoinUserResponse response = userService.insertUser(this.request);
		JoinUserResponse joinedUserResponse = userService.findUserById(response.getId());

		// 행위 검증
		Assertions.assertThat(joinedUserResponse.getId()).isEqualTo(1L);
		verify(userMapper).insertUser(any(User.class));
		verify(userMapper, times(2)).findUserByEmail(any(String.class));
		verify(userMapper).findUserById(any(Long.TYPE));
	}

	@Test
	@DisplayName("없는 아이디로 사용자를 조회")
	public void findUserWithNonExistingId() {
		// 테스트 데이터 및 동작 정의
		Long wrongId = -1L;
		when(userMapper.findUserById(wrongId)).thenReturn(Optional.ofNullable(null));

		// 실행
		assertThrows(NoSuchUserException.class, () -> {
			userService.findUserById(wrongId);
		});

		// 행위 검증
		verify(userMapper).findUserById(wrongId);
	}

	@Test
	@DisplayName("입력된 이메일 주소가 이미 있을 경우")
	public void checkDuplicateEmailExists() {
		// 테스트 데이터 및 동작 정의
		when(userMapper.findUserByEmail(any(String.class))).thenReturn(
			Optional.of(this.request.toUser()));

		// 실행
		assertThrows(DuplicateEmailExistsException.class, () -> {
			userService.insertUser(this.request);
		});

		// 행위 검증
		verify(userMapper).findUserByEmail(any(String.class));
	}

	@Test
	@DisplayName("회원가입 시 입력한 비밀번호는 암호화되어 입력 당시와 달라진다")
	public void encryptPasswordSuccess() {
		// 테스트 데이터 및 동작 정의
		String encryptedPassword = userService.encryptPassword(this.request.getPassword());

		User userWithEncryptedPassword = JoinUserRequest.builder()
			.email(this.request.getEmail())
			.password(encryptedPassword)
			.name(this.request.getName())
			.phoneNumber(this.request.getPhoneNumber())
			.nickname(this.request.getNickname())
			.isAdult(this.request.isAdult())
			.build()
			.toUser();

		doNothing().when(userMapper).insertUser(any(User.class));
		when(userMapper.findUserByEmail(any(String.class))).thenAnswer(new Answer() {
			private int count = 0;

			public Object answer(InvocationOnMock invocation) {
				if (++count == 1) {
					return Optional.ofNullable(null);
				} else {
					return Optional.of(userWithEncryptedPassword);
				}
			}
		});

		// 실행
		userService.insertUser(this.request);

		// 행위 검증
		Assertions.assertThat(encryptedPassword).isNotEqualTo(this.request.getPassword());
		Assertions.assertThat(encryptedPassword).isEqualTo(userService.encryptPassword(this.request.getPassword()));

		verify(userMapper).insertUser(any(User.class));
		verify(userMapper, times(2)).findUserByEmail(any(String.class));
	}

	@Test
	@DisplayName("회원가입되지 않은 이메일로 로그인 시 로그인 실패")
	public void loginFailEmailNotFound() throws Exception {
		// 테스트 데이터 및 동작 정의
		LoginUserRequest invalidRequest = LoginUserRequest.builder()
			.email("yet-joined@fmail.com")
			.password("q1w2e3!")
			.build();
		MockHttpSession session = new MockHttpSession();

		when(userMapper.findUserByEmail(any(String.class))).thenReturn(Optional.ofNullable(null));

		// 실행
		assertThrows(NoSuchUserException.class, () -> {
			userService.loginUser(invalidRequest, session);
		});

		// 행위 검증
		verify(userMapper).findUserByEmail(any(String.class));
	}

	@Test
	@DisplayName("등록된 사용자이더라도 로그인 시 입력한 비밀번호를 암호화했을 때 DB에 저장된 값과 일치하지 않으면 로그인 불가")
	public void loginFailPasswordNotMatch() throws Exception {
		// 테스트 데이터 및 동작 정의
		LoginUserRequest invalidRequest = LoginUserRequest.builder()
			.email("joined@fmail.com")
			.password("cant-remember!")
			.build();

		String validPassword = "valid-password";
		String encryptedValidPassword = userService.encryptPassword(validPassword);

		User validUser = User.builder()
			.email("joined@fmail.com")
			.password(encryptedValidPassword)
			.name("joined")
			.phoneNumber("010-1010-1010")
			.nickname("joi")
			.isAdult(true)
			.createdAt(LocalDateTime.now())
			.build();

		MockHttpSession session = new MockHttpSession();

		when(userMapper.findUserByEmail(any(String.class))).thenReturn(Optional.of(validUser));

		// 실행
		assertThrows(PasswordNotMatchException.class, () -> {
			userService.loginUser(invalidRequest, session);
		});

		// 행위 검증
		verify(userMapper, times(1)).findUserByEmail(any(String.class));
	}

	@Test
	@DisplayName("로그인 성공 테스트")
	public void loginSuccess() {
		// 테스트 데이터 및 동작 정의
		String validPassword = "q1w2e3!";
		String encryptedValidPassword = userService.encryptPassword(validPassword);

		LoginUserRequest validRequest = LoginUserRequest.builder()
			.email("joined@fmail.com")
			.password(validPassword)
			.build();
		User validUser = User.builder()
			.email("joined@fmail.com")
			.password(encryptedValidPassword)
			.name("joined")
			.phoneNumber("010-1010-1010")
			.nickname("joi")
			.isAdult(true)
			.createdAt(LocalDateTime.now())
			.build();

		MockHttpSession session = new MockHttpSession();

		when(userMapper.findUserByEmail(any(String.class))).thenReturn(Optional.of(validUser));

		// 실행
		userService.loginUser(validRequest, session);

		// 행위 검증
		verify(userMapper).findUserByEmail(any(String.class));
	}
}