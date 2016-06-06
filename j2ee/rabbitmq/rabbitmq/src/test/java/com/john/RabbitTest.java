package com.john;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RabbitTest {
	Logger logger = LoggerFactory.getLogger(RabbitTest.class);

	final static String QUEUE_NAME = "my_queue";

	/**
	 * 消息生产者
	 */
	@Test
	public void sendTest() {// 发送消息
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 消息消费者
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	//这里最好不要用单元测试,要不然跑不出来结果
	public static void main(String[] args) throws IOException,
			InterruptedException, TimeoutException {//接收消息
		ConnectionFactory factory = new ConnectionFactory();//新建一个链接工厂
		factory.setHost("192.168.1.78");//设置rabbit服务地址
		
		Connection connection = factory.newConnection();//连接工厂根据上面的ip地址新建一个rabbitmq链接
		Channel channel = connection.createChannel();//创建一个通道

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);//通道声明要处理的队列名称
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {//创建一个通道的消费者
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					AMQP.BasicProperties properties, byte[] body)
					throws IOException {//声明消费这的处理消息的动作
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);//通道开启基础消费
		
		//用完后关闭资源
		channel.close();
		connection.close();
	}
}
