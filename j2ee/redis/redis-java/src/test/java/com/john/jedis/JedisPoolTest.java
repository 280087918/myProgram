package com.john.jedis;

import java.util.List;

import org.junit.Test;

import com.john.base.BaseTest;
import com.john.utils.RedisUtil;

public class JedisPoolTest extends BaseTest {
	@Test
	public void test1() {
		List<String> list = RedisUtil.getJedis().lrange("student", 0, -1);
		log.info("list:{}", list);
	}
}
