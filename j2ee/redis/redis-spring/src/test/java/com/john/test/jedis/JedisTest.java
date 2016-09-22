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
		redisTemplate.opsForValue().set("templateKey004", "测试4");
		log.info("value:{}", redisTemplate.opsForValue().get("templateKey004"));
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
	
	//----------------超时测试 begin
	@Test
	public void test4() {
		redisTemplate.opsForValue().set("t1", "v1", 8, TimeUnit.SECONDS);
		redisTemplate.opsForValue().set("t2", "v2", 3, TimeUnit.SECONDS);
		redisTemplate.opsForValue().set("t3", "v3", 7, TimeUnit.SECONDS);
		redisTemplate.opsForValue().set("t0", "v0", 100, TimeUnit.MILLISECONDS);
	}
	
	@Test
	public void test5() {
		String s = redisTemplate.opsForValue().get("t1");
		log.info("s:{}", s);
	}
	
	@Test
	public void test6() {
		redisTemplate.opsForValue().set("ffzx:es:t1", "v1", 3, TimeUnit.SECONDS);
	}
	
	//----------------超时测试 end
}
