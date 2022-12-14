package com.flab.sooldama.domain.user.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.sooldama.domain.user.dao.UserMapper;
import com.flab.sooldama.domain.user.domain.User;
import com.flab.sooldama.domain.user.dto.request.LoginUserRequest;
import com.flab.sooldama.domain.user.service.UserService;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	@BeforeEach
	public void setUp()
		throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		userMapper.deleteAllUsers();
		String validPassword = "q1w2e3!";

		UserService passwordEncryptor = new UserService(userMapper);
		Method method = passwordEncryptor.getClass().getDeclaredMethod("encryptPassword", String.class);
		method.setAccessible(true);

		String encryptedValidPassword = (String) method.invoke(passwordEncryptor, validPassword);

		User user = User.builder()
			.email("joined@fmail.com")
			.password(encryptedValidPassword)
			.name("joined user")
			.phoneNumber("010-1010-1010")
			.nickname("joined")
			.isAdult(true)
			.build();
		userMapper.insertUser(user);
	}

	@Test
	@DisplayName("?????????????????? ?????? ???????????? ????????? ??? ????????? ??????")
	public void loginFailEmailNotFound() throws Exception {
		// ????????? ????????? ??? ?????? ??????
		LoginUserRequest invalidRequest = LoginUserRequest.builder()
			.email("yet-joined@fmail.com")
			.password("not-joined-yet!")
			.build();

		String content = objectMapper.writeValueAsString(invalidRequest);
		MockHttpSession session = new MockHttpSession();

		// ??????
		mockMvc.perform(post("/users/login")
				.content(content)
				.session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("????????? ????????????????????? ???????????? ????????? ????????? ??????")
	public void loginFailPasswordNotMatch() throws Exception {
		// ????????? ????????? ??? ?????? ??????
		LoginUserRequest invalidRequest = LoginUserRequest.builder()
			.email("joined@fmail.com")
			.password("cant-remember")
			.build();

		String content = objectMapper.writeValueAsString(invalidRequest);
		MockHttpSession session = new MockHttpSession();

		// ??????
		mockMvc.perform(post("/users/login")
				.content(content)
				.session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("????????? ?????? ?????????")
	public void loginSuccessTest() throws Exception {
		// ????????? ????????? ??? ?????? ??????
		LoginUserRequest validRequest = LoginUserRequest.builder()
			.email("joined@fmail.com")
			.password("q1w2e3!")
			.build();

		String content = objectMapper.writeValueAsString(validRequest);
		MockHttpSession session = new MockHttpSession();

		// ??????
		mockMvc.perform(post("/users/login")
				.content(content)
				.session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
	}

}
