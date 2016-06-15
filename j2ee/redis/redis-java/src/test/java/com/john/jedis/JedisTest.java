package com.john.jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.john.base.BaseTest;

public class JedisTest extends BaseTest {
	private Jedis jedis;
	
	@Before
	public void init() {
		//连接redis服务器，127.0.0.1:6379
		jedis = new Jedis("192.168.1.84", 6379);
	}
	
	@Test//存储字符串
	public void stringTest() {
		jedis.set("name", "Jonathan");
		log.info("name:{}", jedis.get("name"));
		
		jedis.append("name", " 好运常在");//追加值
		log.info("name:{}", jedis.get("name"));
		
		//jedis.del("name");//删除键值
		//log.info("name:{}", jedis.get("name"));
	}
	
	@Test//测试map
	public void mapTest() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("country", "中国");
		map.put("province", "广东");
		map.put("city", "深圳");
		jedis.hmset("addr", map);
		log.info("city:{}", jedis.hmget("addr", "city"));
		
		jedis.hdel("addr", "city");//删除某个键值
		log.info("city:{}", jedis.hmget("addr", "city"));
	}
	
	@Test//测试list
	public void listTest() {
		jedis.del("student");//每次测试前都清空一下这个key
		jedis.lpush("student", "john");//lpush:集合的左边方向插入
		jedis.lpush("student", "bob");
		jedis.lpush("student", "christ");
		
		List<String> list = jedis.lrange("student", 0, -1);//最后一个参数如果是-1的话表示全部
		log.info("list:{}", list);
		
		jedis.del("student");//每次测试前都清空一下这个key
		jedis.rpush("student", "john");//lpush:集合的右边边方向插入
		jedis.rpush("student", "bob");
		jedis.rpush("student", "christ");
		
		list = jedis.lrange("student", 0, -1);//最后一个参数如果是-1的话表示全部
		log.info("list:{}", list);
	}
	
	@Test//测试set
	public void setTest() {
		jedis.sadd("user", "张三");
		jedis.sadd("user", "李四");
		jedis.sadd("user", "王五");
		jedis.sadd("user", "赵六");
		jedis.sadd("user", "某某");
		
		jedis.srem("user", "某某");//删除某一个元素
		
		log.info("集合中的所有元素{}",jedis.smembers("user"));
		log.info("‘某某’是否在集合中:{}", jedis.sismember("user", "某某"));
		log.info("随机抽取一个用户:{}", jedis.srandmember("user"));
		log.info("集合大小【{}】", jedis.scard("user"));
	}
}
