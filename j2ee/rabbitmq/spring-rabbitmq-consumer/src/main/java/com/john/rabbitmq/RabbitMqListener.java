package com.john.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class RabbitMqListener implements MessageListener {
	/*public void listen(String message) {
		System.out.println("msg:" + message);
	}*/

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		System.out.println("有消息......" + new String(message.getBody()));
	}
}
