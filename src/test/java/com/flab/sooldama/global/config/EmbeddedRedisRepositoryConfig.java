package com.flab.sooldama.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.test.context.ActiveProfiles;

/*
@EnableRedisRepositories
@EnableRedisRepositories는 redis를 repository로 이용할 수 있도록 설정합니다. 그러면 객체 형태의 데이터를 변환하고
redis hash에 저장할 수 있습니다.
 */

@Configuration
@ActiveProfiles("test")
@EnableRedisRepositories
public class EmbeddedRedisRepositoryConfig {

	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private int redisPort;

	@Bean
	public RedisConnectionFactory embeddedRedisConnectionFactory() {
		return new LettuceConnectionFactory(redisHost, redisPort);
	}

	@Bean
	public RedisTemplate<?, ?> embeddedRedisTemplate() {
		RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(embeddedRedisConnectionFactory());
		return redisTemplate;
	}
}