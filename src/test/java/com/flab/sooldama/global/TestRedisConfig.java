package com.flab.sooldama.global;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@Configuration
@EnableRedisHttpSession
public class TestRedisConfig {

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		System.out.println("TEST FACTORY");
		RedisConnectionFactory factory = Mockito.mock(RedisConnectionFactory.class);
		RedisConnection connection = Mockito.mock(RedisConnection.class);
		Mockito.when(factory.getConnection()).thenReturn(connection);

		return factory;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		System.out.println("TEST TEMPLATE");
		RedisTemplate<String, Object> redisTemplate = Mockito.mock(RedisTemplate.class);
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(Mockito.mock(StringRedisSerializer.class));
		redisTemplate.setValueSerializer(Mockito.mock(StringRedisSerializer.class));
		return redisTemplate;
	}
}
