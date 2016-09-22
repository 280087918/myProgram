package com.john.rabbitmq.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

/**
 * 消息延迟机制
 * @author zhang.hc
 * @date 2016年9月18日 下午3:53:10
 */
public class RabbitPeriod {
	
	/**
	 * 延迟消息队列，客户段接收时请接收队列名称为【QUEUE_NAME + "_DEATH"】,路由key为【ROUTING_KEY + "_DEATH"】
	 * @param EXCHANGE_NAME
	 * @param QUEUE_NAME
	 * @param ROUTING_KEY
	 * @param obj
	 * @param period
	 * @return
	 */
	public static boolean sendPeriodMessage(String EXCHANGE_NAME, String QUEUE_NAME, String ROUTING_KEY, Object obj, Integer period) {
		String DEATH_QUEUE_NAME = QUEUE_NAME + ".DEATH";
		String DEATH_ROUTING_KEY = ROUTING_KEY + ".DEATH";
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("192.168.22.182");
			
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			
			//声明一个exchange，并且类型为direct
			channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
			
			//DEATH参数声明
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("x-dead-letter-exchange", EXCHANGE_NAME);
			args.put("x-dead-letter-routing-key", DEATH_ROUTING_KEY);
			
			//消息属性声明
			Map<String,Object> headersMap = new HashMap<String,Object>();
			headersMap.put("__TypeId__", obj.getClass().getName());
			AMQP.BasicProperties.Builder propsBuilder = new AMQP.BasicProperties.Builder();
			AMQP.BasicProperties basicProperties = propsBuilder.expiration(period.toString()).headers(headersMap).build();
			
			//声明延迟队列
			channel.queueDeclare(QUEUE_NAME, true, false, false, args);
			//延迟队列绑定exchange
			channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
			
			//DEATH队列声明
			channel.queueDeclare(DEATH_QUEUE_NAME, true, false, false, null);
			//DEATH队列绑定exchange
			channel.queueBind(DEATH_QUEUE_NAME, EXCHANGE_NAME, DEATH_ROUTING_KEY);
			
			/**
			 * 将对象转换为Message
			 */
			MessageConverter converter = new JsonMessageConverter();
			MessageProperties messageProperties = new MessageProperties();
			messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
			messageProperties.setType(String.class.getName());
			Message message = converter.toMessage(obj, messageProperties);
			
			//发送消息到延迟队列中
			channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, basicProperties, message.getBody());
			
			//关闭资源
			channel.close();
			connection.close();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
