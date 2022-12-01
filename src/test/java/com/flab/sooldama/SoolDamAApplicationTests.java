package com.flab.sooldama;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

//@SpringBootTest 어노테이션은 통합 테스트를 제공하는 기본적인 스프링부트 테스트 어노테이션입니다.
@ActiveProfiles("test")
@SpringBootTest
class SoolDamAApplicationTests {

	/*
	@Test 어노테이션은 테스트를 수행하는 메소드라는 것을 알려줍니다.
	JUnit 은 각각의 테스트가 서로 영향을 주지 않고 독립적으로 실행됨을 원칙으로 @Test 마다 객체를 생성합니다.
	 */
	@Test
	void contextLoads() {
	}
}
