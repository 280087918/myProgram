package com.john.test.c.exchange.topic;

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
 * topic接收者
 * 	使用topic模式的时候，接受者可以按照routing key的规则来决定要不要接收信息
 *  *占位符只代表一个单词
 *  #占位符代表可代表多个单词
 * @author zhang.hc
 * @date 2016年6月13日 下午7:45:43
 */
public class ReceiveLogsTopic1 {
static final String EXCHANGE_NAME = "zhc_topic_logs";
	
	static final String ROUTING_KEY = "*.stock.*";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.1.78");

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// 声明一个exchange
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String queueName = channel.queueDeclare().getQueue();
		//获取一个队列，并且跟交换器绑定
		channel.queueBind(queueName, EXCHANGE_NAME, ROUTING_KEY);
		
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("ReceiveLogsTopic1 [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}
}
