package com.john.test.c.exchange.fanout;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * fanout模式接收日志，可开启多个线程看效果。
 * 发现只要发送了日志信息，每个fanout的线程都能接收得到
 * @author zhang.hc
 * @date 2016年6月13日 下午5:38:59
 */
public class ReceiveLogs2 {
	static final String EXCHANGE_NAME = "xxx.fanout";

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.1.195");

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// 声明一个exchange
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		String queueName = channel.queueDeclare().getQueue();
		System.out.println("queueName2:" + queueName);
		//获取一个队列，并且跟交换器绑定,fanout模式忽略最后的那个routing key参数
		channel.queueBind(queueName, EXCHANGE_NAME, "");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}
}
