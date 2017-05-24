package com.john.tx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.john.tx.dao.TxDao;
import com.john.tx.dto.TxDto;
import com.john.tx.service.TxService;
import com.john.utils.BizException;

@Service
public class TxServiceImpl implements TxService {
	@Autowired
	private TxDao txDao;
	
	
	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ)
	public void save(TxDto txDto) {
		txDao.save(txDto);
	}

	@Override
	public List<TxDto> findList() {
		List<TxDto> txs = txDao.queryList();
		return txs;
	}
	
	@Transactional
	@Override
	public void updateLongTime(TxDto txDto, long time) {
		int udCount = txDao.update(txDto);
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("更新【" + udCount + "】条数据");
	}
	
	@Transactional(rollbackFor=BizException.class)
	@Override
	public void updateWithTrans(TxDto txDto) throws BizException {
		txDao.update(txDto);
		
		if(null != txDto && txDto.getNum().equals(4)) {
			throw new BizException("number不能为4", "er001", txDto);
		}
	}
}