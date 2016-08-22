package com.john.test.rabbitmq;

import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.test.BaseTest;

/**
 * rabbitMq整合Spring测试
 * @author zhang.hc
 * @date 2016年6月7日 下午12:00:11
 */
public class RabbitTest extends BaseTest {
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Test
	public void testProduce() {
		String msg = "测试";
//		amqpTemplate.convertAndSend(msg);
//		amqpTemplate.convertAndSend("wms.stock.currentNum", msg);
		//rabbitTemplate.setExchange("my-mq-exchange");
		rabbitTemplate.convertAndSend("wms.stock.currentNum", msg);
	}
}
