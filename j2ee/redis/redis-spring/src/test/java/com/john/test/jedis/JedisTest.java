package com.john.test.jedis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.john.test.BaseTest;

public class JedisTest extends BaseTest {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Test
	public void test1() {
		redisTemplate.opsForValue().set("templateKey002", "测试2");
		
		log.info("value:{}", redisTemplate.opsForValue().get("templateKey002"));
	}
}
