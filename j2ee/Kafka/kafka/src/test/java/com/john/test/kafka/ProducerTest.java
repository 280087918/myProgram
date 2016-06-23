package com.john.test.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import com.john.test.BaseTest;

/**
 * 简单的生产者测试
 * @author zhang.hc
 * @date 2016年6月15日 下午7:49:30
 */
public class ProducerTest extends BaseTest {
	@Test
	public void testSend() {
		//设置配置信息，用来创建KafkaProducer的时候用
		Properties properties = new Properties();
		properties.put("bootstrap.servers", "192.168.1.84:9092");
		properties.put("acks", "all");
		properties.put("retries", 0);
		properties.put("batch.size", 16384);
		properties.put("linger.ms", 1);
		properties.put("buffer.memory", 33554432);
		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		//根据上面的配置创建一个KafkaProducer
		Producer<String, String> producer = new KafkaProducer<String, String>(properties);
		
		for (int i = 0; i < 20; i++) {
			producer.send(new ProducerRecord<String, String>("hc_topic", "key_" + i, "value_" + i));
			log.info("send({})", i);
		}
		
		//关闭资源
		producer.close();
	}
}
