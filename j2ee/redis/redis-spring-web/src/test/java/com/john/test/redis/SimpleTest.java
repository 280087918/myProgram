package com.john.test.redis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.john.test.BaseTest;

public class SimpleTest extends BaseTest {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void testSave() {
		redisTemplate.opsForValue().set("test_key", "value001");
	}
	
	@Test
	public void testGet() {
		String value = (String) redisTemplate.opsForValue().get("test_key");
		log.info("value:{}", value);
	}
	
	@Test
	public void testDel() {
		redisTemplate.delete("test_key");
	}
}
