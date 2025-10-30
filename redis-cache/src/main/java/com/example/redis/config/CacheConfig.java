package com.example.redis.config;

import java.time.Duration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
@EnableCaching
public class CacheConfig {
	@Bean
	public RedisCacheManager cacheManager(
		RedisConnectionFactory redisConnectionFactory
	) {
		// 설정 구성을 먼저 진행한다.
		// redis를 이용해서 Spring Cache를 사용할 때
		// Redis 관련 설정을 모아두는 클래스
		RedisCacheConfiguration configuration = RedisCacheConfiguration
			.defaultCacheConfig()
			// null을 캐싱하는지
			.disableCachingNullValues()
			// 기본 캐시 유지 시간 (Time to Live) - 10초 이후에 조회가 들어오면 원본을 조회
			.entryTtl(Duration.ofSeconds(120))
			// 캐시를 구분하는 방법 설정 : 접두사 실행
			.computePrefixWith(CacheKeyPrefix.simple())
			// 캐시에 저장할 값을 어떻게 직렬화 / 역질렬화 할 것인지 -> java로
			.serializeValuesWith(
				SerializationPair.fromSerializer(RedisSerializer.java())
			);

		return RedisCacheManager
			.builder(redisConnectionFactory)
			.cacheDefaults(configuration) // 이 설정을 기본으로 한다.
			.build();
	}
}
