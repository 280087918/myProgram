package com.john.test.c.exchange.direct;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * direct模式，跟fanout模式最大的不同是多了个routing key的参数去路由消息
 * @author zhang.hc
 * @date 2016年6月13日 下午5:39:43
 */
public class EmitLogDirect {
	static final String EXCHANGE_NAME = "zhc_direct_logs";
	
	//info;waring;error.
	static final String ROUTING_KEY = "info";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.22.188");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		//声明一个exchange，并且类型为direct
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		String message = "here is the 【" + ROUTING_KEY + "】 message sending from zhc server......";
		
		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
		
		//关闭资源
		channel.close();
		connection.close();
		
		System.out.println("消息发送完毕......");
	}
}
