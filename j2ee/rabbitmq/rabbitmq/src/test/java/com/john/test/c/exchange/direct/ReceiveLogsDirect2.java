package com.john.test.c.exchange.direct;

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
 * direct模式接收参数
 * 	跟上一个接收测试没差别，只是ROUTING_KEY不一样，可以开启两个去测试是否根据ROUTING_KEY去接收的
 * @author zhang.hc
 * @date 2016年6月13日 下午5:49:22
 */
public class ReceiveLogsDirect2 {
static final String EXCHANGE_NAME = "zhc_direct_logs";
	
	//info;waring;error.
	static final String ROUTING_KEY = "waring";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.2.184");

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// 声明一个exchange
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		String queueName = channel.queueDeclare().getQueue();
		//获取一个队列，并且跟交换器绑定
		channel.queueBind(queueName, EXCHANGE_NAME, ROUTING_KEY);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("ReceiveLogsDirect2 [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}
}
