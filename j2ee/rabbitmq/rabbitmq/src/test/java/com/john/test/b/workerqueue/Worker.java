package com.john.test.b.workerqueue;

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
 * 任务处理测试-接收处理
 * @author zhang.hc
 * @date 2016年6月7日 下午8:13:25
 */
public class Worker {
	private final static String QUEUE_NAME = "my_queue";
	
	static Connection connection = null;
	static Channel channel = null;

	public static void main(String[] argv) throws java.io.IOException,
			java.lang.InterruptedException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.1.78");
		connection = factory.newConnection();
		channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		
		/**
		 * 这是一个优化的小特性，为了解决consumer在处理消息的时候不公平的问题
		 * 	  比如：比如一个队列对应两个consumer，消息刚好在odd(基数)的时候消息特别大，而在even(偶数)的时候特别小
		 * 			就会导致处理基数消息的consumer一致在处理消息，而even的很快处理完了。消息就堵在了基数消息处理的consumer那里
		 * 解决:设置这个参数意思就是本consumer一次只接收一个消息，在处理完之前不接收，让其他consumer接收。
		 */
		channel.basicQos(1);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");

				try {
					doWork(message);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.out.println(" [x] Done");
					/**
					 * 这个还没理解透彻，目前就是这么设置就好了。boolean设置为false
					 * 因为下面设置了不自动给broker发送回执，所以程序执行完了之后，在finally块给broker一个收到的回执
					 */
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		//boolean参数是指是否收到消息后立马给broker一个收到消息的回执
		channel.basicConsume(QUEUE_NAME, false, consumer);
	}

	private static void doWork(String task) throws InterruptedException {
		for (char ch : task.toCharArray()) {
			if (ch == '.')
				Thread.sleep(1000);
		}
	}
	
	@Override
	protected void finalize() throws Throwable {//关闭资源
		if(null != channel) {
			channel.close();
		}
		if(null != connection) {
			connection.close();
		}
		super.finalize();
	}
}