package com.john.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.john.utils.JedisLock;

@Component
public class MyRedisService {
	Logger log = LoggerFactory.getLogger(MyRedisService.class);
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	public void processMyProcess() {
		String transactionProcessingVarName = null;
		JedisLock jedisLock = null;
		log.info("【{}】开始", Thread.currentThread().getName());
		try {
			String varName = "lock_test:lock_key_001";
			transactionProcessingVarName = varName.concat("_processing");
			int timeoutMs = 30000;
			int expireMs = 3000;
			// 准备分布式锁。
			jedisLock = new JedisLock(redisTemplate, varName, timeoutMs, expireMs);
			// 加锁。
			/**
			 * 这里面分了两把锁
			 * 1:等待锁 用来给其他线程有一个等待的机会
			 * 2:业务锁 具体业务执行的时候需要执行的时间
			 */
			if (jedisLock.acquire() && redisTemplate.opsForValue().setIfAbsent(transactionProcessingVarName, "0")) {
				log.info("线程【{}】锁定中......", Thread.currentThread().getName());
				jedisLock.setParentHashCode(this.hashCode());
				
				long timeCount = 50;
				while(timeCount > 0) {
					log.info("线程【{}】在执行中......", Thread.currentThread().getName());
					Thread.sleep(500);
					timeCount -= 10;
				}
				
				redisTemplate.delete(transactionProcessingVarName);
				//释放锁。 
				jedisLock.release();
				log.info("正常解锁【{}】", Thread.currentThread().getName());
			} else {
				log.info("没等到锁的线程【{}】，终止了.", Thread.currentThread().getName());
			}
		} catch (Exception ex) {
			//异常时当锁对象的父hashCode不为空时，则释放业务执行标识。
			if(redisTemplate != null && jedisLock.getParentHashCode() != 0) {
				redisTemplate.delete(transactionProcessingVarName);
				log.info("删除标识");
			}
			
			// 异常时当锁对象的父hashCode不为空时，则释放业务执行标识。
			if (redisTemplate != null && jedisLock.getParentHashCode() != 0) {
				redisTemplate.delete(transactionProcessingVarName);
				log.info("删除标识");
			}
			// 释放锁。
			if (jedisLock != null && jedisLock.isLocked()) {
				jedisLock.release();
				log.info("异常解锁: 已失败");
			}
		} finally {
			// 释放锁。
			if (jedisLock != null && jedisLock.isLocked()) {
				jedisLock.release();
				log.info("超时解锁【{}】", Thread.currentThread().getName());
			}
		}
	}
}
