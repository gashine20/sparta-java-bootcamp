package com.example.redis;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootTest
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
class RedisApplicationTests {

	@Test
	void contextLoads() {
	}

}
