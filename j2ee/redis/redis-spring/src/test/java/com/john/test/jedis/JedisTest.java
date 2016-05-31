package com.john.test.jedis;

import java.util.concurrent.TimeUnit;

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
	
	@Test
	public void del() {
		log.info("value1:{}", redisTemplate.opsForValue().get("templateKey002"));
		redisTemplate.delete("templateKey002");
		log.info("value2:{}", redisTemplate.opsForValue().get("templateKey002"));
	}
	
	@Test//这个可以作为分布式锁的一种使用场景
	public void test2() {
		boolean ud = redisTemplate.opsForValue().setIfAbsent("key0524", "测试value1");
		log.info("更新结果:" + ud);
		redisTemplate.expire("key0524", 10, TimeUnit.SECONDS);
	}
	
	@Test
	public void test3() {
		log.info("key0524_(1):{}", redisTemplate.opsForValue().get("key0524"));
		boolean ud = redisTemplate.opsForValue().setIfAbsent("key0524", "测试value2");
		log.info("更新结果:" + ud);
		log.info("key0524_(2):{}", redisTemplate.opsForValue().get("key0524"));
		
		redisTemplate.delete("key0524");
	}
}
