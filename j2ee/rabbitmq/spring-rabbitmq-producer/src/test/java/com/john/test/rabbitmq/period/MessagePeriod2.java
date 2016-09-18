package com.john.test.rabbitmq.period;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消息延迟声明到队列当中
 * @author zhang.hc
 * @date 2016年9月18日 下午3:46:32
 */
public class MessagePeriod2 {
	Logger log = LoggerFactory.getLogger(MessagePeriod2.class);
	
	static final String EXCHANGE_NAME = "PERIOD_EX";
	
	static final String ROUTING_KEY = "DELAY.ROUTING.KEY";	
	static final String QUEUE_NAME = "DELAY.QUEUE";
	
	static final String DEATH_ROUTING_KEY = "DEATH.ROUTING.KEY";
	static final String DEATH_QUEUE_NAME = "DEATH.QUEUE";
	
	@Test
	public void test() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("192.168.22.182");
			
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			
			String message = "here is the message sending from zhc server......";
			
			//声明一个exchange，并且类型为direct
			channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
			
			//DEATH参数声明
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("x-message-ttl", 5000);
			args.put("x-dead-letter-exchange", EXCHANGE_NAME);
			args.put("x-dead-letter-routing-key", DEATH_ROUTING_KEY);
			
			//声明延迟队列
			channel.queueDeclare(QUEUE_NAME, true, false, false, args);
			//延迟队列绑定exchange
			channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
			
			//DEATH队列声明
			channel.queueDeclare(DEATH_QUEUE_NAME, true, false, false, null);
			//DEATH队列绑定exchange
			channel.queueBind(DEATH_QUEUE_NAME, EXCHANGE_NAME, DEATH_ROUTING_KEY);
			
			//发送消息到延迟队列中
			channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
			
			//关闭资源
			channel.close();
			connection.close();
		} catch (Exception e) {
			log.info("发送消息异常.");
			e.printStackTrace();
		}
	}
}
