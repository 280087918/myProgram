package com.john.test.transaction;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.test.BaseTest;
import com.john.tx.dto.TxDto;
import com.john.tx.service.TxService;
import com.john.utils.BizException;

public class TxTransactionTest extends BaseTest {
	@Autowired
	private TxService txService;
	
	@Test
	public void testSave() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				txService.save(new TxDto(1, 1, 1));
			}
		});
		
		t1.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdate() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				txService.updateLongTime(new TxDto(1, 2, 1), 2000);
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				txService.updateLongTime(new TxDto(1, 3, 1), 4000);
			}
		});
		t1.start();
		t2.start();
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test//对于并发的情况用版本进行控制
	public void testUpdate2() {
		txService.updateLongTime(new TxDto(1, 2, 2), 1000);
	}
	
	@Test
	public void testRollback() {
		try {
			txService.updateWithTrans(new TxDto(1, 4, 1));
		} catch (BizException e) {
			e.printStackTrace();
		}
	}
}
