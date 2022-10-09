package com.flab.sooldama.domain.user.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * AutoConfigureMockMvc 어노테이션은 Mock 테스트시 필요한 의존성을 제공해주는 어노테이션 입니다.
 * DisplayName 은 테스트의 이름을 표시하는 어노테이션입니다.
 * Autowired 는 스프링 DI 에서 사용되는 어노테이션입니다.
 * 스프링에서 빈 인스턴스가 생성된 이후 자동으로 인스턴스를 주입하기 위해 사용합니다.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserApiTest {

    @Autowired MockMvc mockMvc;

    @Test
    @DisplayName("테스트 함수 호출 테스트") // Todo : 삭제 예정
    public void indexTest() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
    }
}
