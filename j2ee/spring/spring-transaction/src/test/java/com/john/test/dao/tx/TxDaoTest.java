package com.john.test.dao.tx;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.test.BaseTestWithDB;
import com.john.tx.dao.TxDao;
import com.john.tx.dto.TxDto;

public class TxDaoTest extends BaseTestWithDB {
	@Autowired
	private TxDao txDao;
	
	@Test
	public void testSave() {
		txDao.save(new TxDto(5, 5, 1));
	}
	
	@Test
	public void testFindList() {
		List<TxDto> txs = txDao.queryList();
		log.info("txs:{}", txs);
	}
	
	@Test
	public void testClear() {
		txDao.clear();
	}
	
	@Test
	public void testQueryMap() {
		List<Map<String, Object>> mapList = txDao.queryMapList();
		System.out.println(mapList);
	}
}
