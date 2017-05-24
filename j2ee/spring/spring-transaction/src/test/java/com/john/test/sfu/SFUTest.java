package com.john.test.sfu;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.sfu.dto.SFUDto;
import com.john.sfu.service.SFUService;
import com.john.test.BaseTest;

public class SFUTest extends BaseTest {
	@Autowired
	private SFUService sFUService;
	
	@Test
	public void testUd() {
		try {
			Thread t1 = new Thread(new Runnable() {
				@Override
				public void run() {
					sFUService.update(new SFUDto(1, 2.0f), 2000);
				}
			});
			Thread t2 = new Thread(new Runnable() {
				@Override
				public void run() {
					sFUService.update(new SFUDto(1, 3.5f), 4000);
				}
			});
			t1.start();
			Thread.sleep(200);
			t2.start();
			
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReturn() {
		sFUService.updateReturn(new SFUDto(1, 5f), true);
	}
}
