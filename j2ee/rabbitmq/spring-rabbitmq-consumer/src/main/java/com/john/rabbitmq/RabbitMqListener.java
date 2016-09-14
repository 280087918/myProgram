package com.john.rabbitmq;


import com.ffzx.dto.Message;

public class RabbitMqListener {
	/*public void listen(String message) {
		System.out.println("msg:" + message);
	}*/

	public void onMessage(Message message) {
		// TODO Auto-generated method stub
//		System.out.println("有消息......" + new String(message.getBody()));
		System.out.println("有消息......" + message);
	}
}
