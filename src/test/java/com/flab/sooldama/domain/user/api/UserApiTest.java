package com.flab.sooldama.domain.user.api;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.sooldama.domain.user.dto.request.JoinUserRequest;
import com.flab.sooldama.domain.user.dto.request.LoginUserRequest;
import com.flab.sooldama.domain.user.exception.DuplicateEmailExistsException;
import com.flab.sooldama.domain.user.exception.FailToEncryptPasswordException;
import com.flab.sooldama.domain.user.exception.NoSuchUserException;
import com.flab.sooldama.domain.user.exception.PasswordNotMatchException;
import com.flab.sooldama.domain.user.service.UserService;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

/**
 * AutoConfigureMockMvc 어노테이션은 Mock 테스트시 필요한 의존성을 제공해주는 어노테이션 입니다. DisplayName 은 테스트의 이름을 표시하는
 * 어노테이션입니다. Autowired 는 스프링 DI 에서 사용되는 어노테이션입니다. 스프링에서 빈 인스턴스가 생성된 이후 자동으로 인스턴스를 주입하기 위해 사용합니다.
 */

/*
 * @WebMvcTest 어노테이션은 MVC 와 관련된 요소만을 테스트할 수 있도록 도와줍니다. @SpringBootTest 어노테이션을 사용하면
 * Service 의존성도 가져오므로 독립적인 Controller 단위 테스트를 할 수 없습니다. 반면 @WebMvcTest 어노테이션을 사용하면
 * Service 의존성을 모의 객체로 대신할 수 있어 독립적인 Controller 단위 테스트를 할 수 있습니다.
 * @MockBean 어노테이션은 @WebMvcTest 가 붙은 테스트 클래스에서 모의 객체로 사용할 요소에 붙입니다. 그러면 이 모의 객체가
 * 의존성으로 설정됩니다.
 */
@WebMvcTest(controllers = UserApi.class)
public class UserApiTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@InjectMocks
	private UserApi userApi;

	@MockBean
	private UserService userService;

	@Autowired
	Validator validator;

	@Test
	@DisplayName("회원가입 성공 테스트")
	public void joinSuccessTest() throws Exception {
		//Given 서비스를 거친 결과값
		String content = objectMapper.writeValueAsString(
			JoinUserRequest.builder()
				.email("younghee@fmail.com")
				.password("1q2w3e4r!")
				.name("younghee lee")
				.phoneNumber("010-0101-0101")
				.nickname("yh")
				.isAdult(true)
				.build());

		//Then 회원가입 api에 content를 넣고 호출했을 때
		mockMvc.perform(post("/users")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("필수 정보를 입력하지 않으면 회원가입 처리 불가")
	public void joinFailTest() throws Exception {
		//Given 서비스를 거친 결과값
		String content = objectMapper.writeValueAsString(
			JoinUserRequest.builder()
				.password("1q2w3e4r!")
				.name("younghee lee")
				.build());
		//Then 회원가입 api에 content를 넣고 호출했을 때
		mockMvc.perform(post("/users")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("회원가입 중 예외 발생시 Controller Advice가 처리하는지 확인")
	public void exceptionHandledByControllerAdviceTest() throws Exception {
		// 테스트 데이터 및 동작 정의
		JoinUserRequest request = JoinUserRequest.builder()
			.email("sehoon@fmail.com")
			.password("abracadabra")
			.name("sehoon gim")
			.phoneNumber("010-1010-1010")
			.nickname("sesoon")
			.isAdult(true)
			.build();

		String content = objectMapper.writeValueAsString(request);

		when(userService.insertUser(any(JoinUserRequest.class))).thenThrow(
			DuplicateEmailExistsException.class);

		// 실행
		mockMvc.perform(post("/users")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest());

		// 행위 검증
		verify(userService, times(1)).insertUser(any(JoinUserRequest.class));
	}

	@Test
	@DisplayName("회원가입 중 비밀번호 암호화 실패 시 Controller Advice가 예외를 처리")
	public void encryptionExceptionHandledByControllerAdviceTest() throws Exception {
		// 테스트 데이터 및 동작 정의
		JoinUserRequest request = JoinUserRequest.builder()
			.email("sehoon@fmail.com")
			.password("abracadabra")
			.name("sehoon gim")
			.phoneNumber("010-1010-1010")
			.nickname("sesoon")
			.isAdult(true)
			.build();

		String content = objectMapper.writeValueAsString(request);

		when(userService.insertUser(any(JoinUserRequest.class))).thenThrow(
			FailToEncryptPasswordException.class);

		// 실행
		mockMvc.perform(post("/users")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isInternalServerError());

		// 행위 검증
		verify(userService, times(1)).insertUser(any(JoinUserRequest.class));
	}

	@Test
	@DisplayName("유효하지 않은 이메일 주소일 경우")
	public void checkInvalidEmail() {
		String invalidEmail = "sehoon@fmaildotcom";
		String messageForInvalidEmail = "이메일 형식에 맞지 않습니다";

		JoinUserRequest request = JoinUserRequest.builder()
			.email(invalidEmail)
			.password("abracadabra")
			.name("sehoon gim")
			.phoneNumber("010-1010-1010")
			.nickname("sesoon")
			.isAdult(true)
			.build();

		Set<ConstraintViolation<JoinUserRequest>> validate = validator.validate(request);

		Iterator<ConstraintViolation<JoinUserRequest>> iterator = validate.iterator();
		while (iterator.hasNext()) {
			ConstraintViolation<JoinUserRequest> next = iterator.next();
			Assertions.assertThat(next.getMessage()).contains(messageForInvalidEmail);
		}
	}

	@Test
	@DisplayName("회원가입되지 않은 이메일로 로그인 시 로그인 실패")
	public void loginFailEmailNotFound() throws Exception {
		// 테스트 데이터 및 동작 정의
		LoginUserRequest invalidRequest = LoginUserRequest.builder()
			.email("yet-joined@fmail.com")
			.password("q1w2e3!")
			.build();

		String content = objectMapper.writeValueAsString(invalidRequest);
		MockHttpSession session = new MockHttpSession();

		doThrow(NoSuchUserException.class).when(userService)
			.loginUser(any(LoginUserRequest.class), any(HttpSession.class));

		// 실행
		mockMvc.perform(post("/users/login")
				.content(content)
				.session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest());

		// 행위 검증
		assertThrows(NoSuchUserException.class, () -> {
			userService.loginUser(invalidRequest, session);
		});

		verify(userService, times(2)).loginUser(any(LoginUserRequest.class),
			any(HttpSession.class));
	}

	@Test
	@DisplayName("등록된 사용자이더라도 비밀번호 틀리면 로그인 불가")
	public void loginFailPasswordNotMatch() throws Exception {
		// 테스트 데이터 및 동작 정의
		LoginUserRequest invalidRequest = LoginUserRequest.builder()
			.email("joined@fmail.com")
			.password("q1w2e3!")
			.build();

		String content = objectMapper.writeValueAsString(invalidRequest);
		MockHttpSession session = new MockHttpSession();

		doThrow(PasswordNotMatchException.class).when(userService)
			.loginUser(any(LoginUserRequest.class), any(HttpSession.class));

		// 실행
		mockMvc.perform(post("/users/login")
				.content(content)
				.session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest());

		// 행위 검증
		assertThrows(PasswordNotMatchException.class, () -> {
			userService.loginUser(invalidRequest, session);
		});

		verify(userService, times(2)).loginUser(any(LoginUserRequest.class),
			any(HttpSession.class));
	}

	@Test
	@DisplayName("로그인 성공 테스트")
	public void loginSuccess() throws Exception {
		// 테스트 데이터 및 동작 정의
		LoginUserRequest validRequest = LoginUserRequest.builder()
			.email("joined@fmail.com")
			.password("q1w2e3!")
			.build();

		String content = objectMapper.writeValueAsString(validRequest);
		MockHttpSession session = new MockHttpSession();

		doNothing().when(userService)
			.loginUser(any(LoginUserRequest.class), any(HttpSession.class));

		// 실행
		mockMvc.perform(post("/users/login")
				.content(content)
				.session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());

		// 행위 검증
		verify(userService, times(1)).loginUser(any(LoginUserRequest.class),
			any(HttpSession.class));
	}
}
