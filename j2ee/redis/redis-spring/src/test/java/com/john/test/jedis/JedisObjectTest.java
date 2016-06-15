package com.john.test.jedis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.john.test.BaseTest;
import com.john.vo.Car;

/**
 * 测试用redis模版存储对象
 *    这个底层应该是调用了jedis的set(byte[] key, byte[] value)
 * @author zhang.hc
 * @date 2016年6月15日 上午11:50:37
 */
public class JedisObjectTest extends BaseTest {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void testSet() {
		Car car = new Car("001", "大黄蜂", 2016, true);
		redisTemplate.opsForValue().set("car", car);
	}
	
	@Test
	public void testGet() {
		Car car = (Car) redisTemplate.opsForValue().get("car");
		log.info("car:{}" , car);
	}
}
