package com.flab.sooldama;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
@SpringBootApplication 어노테이션은 auto-configuration 을 담당합니다.
@SpringBootApplication 어노테이션으로인해 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성이 모두 자동으로 설정됩니다.
 */
@SpringBootApplication
public class SoolDamAApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoolDamAApplication.class, args);
	}
}
