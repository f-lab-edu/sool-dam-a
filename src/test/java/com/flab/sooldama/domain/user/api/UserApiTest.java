package com.flab.sooldama.domain.user.api;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.sooldama.domain.user.dto.request.JoinUserRequest;
import com.flab.sooldama.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

/**
 * AutoConfigureMockMvc 어노테이션은 Mock 테스트시 필요한 의존성을 제공해주는 어노테이션 입니다.
 * DisplayName 은 테스트의 이름을 표시하는 어노테이션입니다.
 * Autowired 는 스프링 DI 에서 사용되는 어노테이션입니다.
 * 스프링에서 빈 인스턴스가 생성된 이후 자동으로 인스턴스를 주입하기 위해 사용합니다.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserApiTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private UserService userService;

    @Test
    @DisplayName("테스트 함수 호출 테스트") // Todo : 삭제 예정
    public void indexTest() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    public void joinSuccessTest() throws Exception {
        //Given 서비스를 거친 결과값
        String content = objectMapper.writeValueAsString(
                JoinUserRequest.builder()
                        .id(13L)
                        .email("younghee@fmail.com")
                        .password("1q2w3e4r!")
                        .name("younghee lee")
                        .phoneNumber("010-0101-0101")
                        .nickname("yh")
                        .isAdult(true)
                        .createdAt("2022-10-07")
                        .build());

        //Then 회원가입 api에 content를 넣고 호출했을 때
        mockMvc.perform(post("/join/")
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
        assertThrows(NestedServletException.class, () -> mockMvc.perform(post("/join")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated()));
    }
}
