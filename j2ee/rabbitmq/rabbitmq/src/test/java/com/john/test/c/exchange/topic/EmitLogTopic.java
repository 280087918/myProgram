package com.john.test.c.exchange.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * exchange的主题模式(topic)
 * @author zhang.hc
 * @date 2016年6月13日 下午6:30:37
 */
public class EmitLogTopic {
	static final String EXCHANGE_NAME = "zhc_topic_logs";
	
	static final String ROUTING_KEY = "order.stock.log";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.1.195");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		//声明一个topic的exchange
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		
		String message = "stock is running out......";
		
		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
		
		//关闭资源
		channel.close();
		connection.close();
	}
}
