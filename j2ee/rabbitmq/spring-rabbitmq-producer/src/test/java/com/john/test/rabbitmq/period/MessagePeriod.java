package com.john.test.rabbitmq.period;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessagePeriod {
	Logger log = LoggerFactory.getLogger(MessagePeriod.class);
	
	static final String EXCHANGE_NAME = "PERIOD_EX";
	
	static final String ROUTING_KEY = "DELAY.MESSAGE.TEST";
	
	@Test
	public void test() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("192.168.22.182");
			
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			
			//声明一个exchange，并且类型为direct
			channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
			String message = "here is the 【" + ROUTING_KEY + "】 message sending from zhc server......";
			
			AMQP.BasicProperties.Builder propsBuilder = new AMQP.BasicProperties.Builder();
			AMQP.BasicProperties basicProperties = propsBuilder.expiration("10000").build();
			
			log.info("expiration:{}", basicProperties.getExpiration());
			
			channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, basicProperties, message.getBytes());
			
			//关闭资源
			channel.close();
			connection.close();
		} catch (Exception e) {
			log.info("发送消息异常.");
			e.printStackTrace();
		}
	}
}
