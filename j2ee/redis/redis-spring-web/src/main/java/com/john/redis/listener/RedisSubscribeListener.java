package com.john.redis.listener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyspaceEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

public class RedisSubscribeListener extends KeyspaceEventMessageListener {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public RedisSubscribeListener(RedisMessageListenerContainer listenerContainer) {
		super(listenerContainer);
	}

	@Override
	protected void doHandleMessage(Message message) {
		String key = new String(message.getBody());
		//经过测试，过期的键拿出来的值是null
		System.out.println("key:" + key + ", value:" + redisTemplate.opsForValue().get(key));
		
	}
}
