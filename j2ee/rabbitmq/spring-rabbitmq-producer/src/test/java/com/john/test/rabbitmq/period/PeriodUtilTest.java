package com.john.test.rabbitmq.period;

import org.junit.Test;

import com.ffzx.dto.Message;
import com.john.rabbitmq.utils.RabbitPeriod;

public class PeriodUtilTest {
	@Test
	public void sendObj() {
		Message msg1 = new Message("测试1", 10);
		RabbitPeriod.sendPeriodMessage("PERIOD_EX", "DELAY.QUEUE", "DELAY.ROUTING.KEY", msg1, 10000);
		
		Message msg2 = new Message("测试2", 20);
		RabbitPeriod.sendPeriodMessage("PERIOD_EX", "DELAY.QUEUE", "DELAY.ROUTING.KEY", msg2, 4000);
		
//		Message msg3 = new Message("测试3", 30);
//		RabbitPeriod.sendPeriodMessage("PERIOD_EX", "DELAY.QUEUE", "DELAY.ROUTING.KEY", msg3, 7000);
//		
//		Message msg4 = new Message("测试4", 40);
//		RabbitPeriod.sendPeriodMessage("PERIOD_EX", "DELAY.QUEUE", "DELAY.ROUTING.KEY", msg4, 2000);
//		
//		Message msg5 = new Message("测试5", 50);
//		RabbitPeriod.sendPeriodMessage("PERIOD_EX", "DELAY.QUEUE", "DELAY.ROUTING.KEY", msg5, 10000);
	}
}
