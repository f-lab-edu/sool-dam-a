package com.flab.sooldama.global.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = HealthCheckApi.class)
class HealthCheckApiTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("로드 밸런서 health check 테스트")
	public void healthCheckSuccessTest() throws Exception {
		this.mockMvc
			.perform(get("/healthcheck"))
			.andExpect(status().isOk());
	}
}
