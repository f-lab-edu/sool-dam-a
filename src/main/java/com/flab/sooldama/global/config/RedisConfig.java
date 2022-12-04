package com.flab.sooldama.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/*
@Configuration
@Configuration은 자바 클래스 형태로 스프링 빈을 등록할 수 있게 해줍니다. 예를 들어 스프링 빈으로 만들고자 하는 생성자에
@Bean을 붙이는 식으로 스프링 빈을 등록할 수 있습니다. @Configuration 정의는 @Component를 포함하므로 @Configuration이
붙은 클래스는 컴포넌트 스캔의 대상이 됩니다. 따라서 @Configuration이 붙은 클래스는 @Autowired의 후보가 됩니다.
@Profile로 profile을 명시할 수도 있습니다. profile을 명시할 경우, 명시된 profile이 활성화되어있을 때만
@Configuration 클래스를 연산합니다.

@Profile
@Profile은 @Component가 붙은 클래스를 명시한 profile이 활성화되어 있을 경우에만 등록하도록 허용함을 나타냅니다.
@Profile을 쓸 수 있는 경우는:
1) @Component(또는 @Configuration 같이 자신의 정의에 @Component를 포함하는 어노테이션)가 붙은 클래스에 타입 레벨
어노테이션으로 사용
2) @Bean 메소드에 메소드 레벨 어노테이션으로 사용
등이 있습니다.

@EnableRedisHttpSession
@EnableRedisHttpSession을 @Configuration 클래스에 붙이면 세션 데이터를 Redis에 저장합니다.
이 어노테이션을 쓰려면 RedisConnectionFactory가 반드시 있어야 합니다.
 */

@Profile("dev")
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = RedisConfig.EXPIRE_TIME)
public class RedisConfig {

	static final int EXPIRE_TIME = 60;

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port));
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		return redisTemplate;
	}
}
