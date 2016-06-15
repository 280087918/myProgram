package com.john.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.rabbitmq.client.Channel;



public class RabbitMqListenerWithChannel implements ChannelAwareMessageListener {

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		System.out.println("有消息......" + new String(message.getBody()));
		//这里用来发确认消息
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
}
