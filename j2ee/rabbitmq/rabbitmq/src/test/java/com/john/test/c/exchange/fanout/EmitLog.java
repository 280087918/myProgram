package com.john.test.c.exchange.fanout;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 交换器Exchange之广播模式fanout
 * @author zhang.hc
 * @date 2016年6月13日 下午2:57:37
 */
public class EmitLog {
	static final String EXCHANGE_NAME = "zhc_logs";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.1.78");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		//声明一个exchange
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		
		String message = "这是一个log日志";
		
		//basicPublish(exchange, routingKey, props, body)
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		
		channel.close();
		connection.close();
	}
}
