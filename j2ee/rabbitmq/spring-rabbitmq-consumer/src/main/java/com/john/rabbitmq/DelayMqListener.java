package com.john.rabbitmq;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;




@Service("delayMqListener")
public class DelayMqListener implements ChannelAwareMessageListener {
	Logger log = LoggerFactory.getLogger(DelayMqListener.class);
	
//	public void onMessage(Object message) {
//		System.out.println("有消息......" + message);
//	}

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		String msg = new String(message.getBody());
		Gson gson = new Gson();
		com.ffzx.dto.Message myMessage = gson.fromJson(msg, com.ffzx.dto.Message.class);
		log.info("收到消息{}", myMessage);
	}
}
