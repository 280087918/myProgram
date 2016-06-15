package com.john.test.a.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 简单队列测试-发送
 * @author zhang.hc
 * @date 2016年6月7日 下午4:54:40
 */
public class Send {
	static Logger logger = LoggerFactory.getLogger(Send.class);
	
	final static String QUEUE_NAME = "my_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();//新建一个链接工厂
		factory.setHost("192.168.1.78");//设置rabbit服务地址
		Connection connection = factory.newConnection();//连接工厂根据上面的ip地址新建一个rabbitmq链接
		Channel channel = connection.createChannel();//创建一个通道

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);//通道声明要处理的队列名称
		
		String message = "Hello 皓诚!";
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());//通道里面发送消息
		logger.info("send '{}'", message);

		//用完后关闭资源
		channel.close();
		connection.close();
	}
}
