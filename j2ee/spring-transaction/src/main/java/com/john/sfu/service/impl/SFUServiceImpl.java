package com.john.sfu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.john.sfu.dao.SFUDao;
import com.john.sfu.dto.SFUDto;
import com.john.sfu.service.SFUService;

@Service
public class SFUServiceImpl implements SFUService {
	@Autowired
	private SFUDao sFUDao;
	
	@Transactional
	@Override
	public void update(SFUDto udDto, long time) {
		SFUDto dbDto = sFUDao.selcetForUpdate(udDto.getId());
		dbDto.setAmount(udDto.getAmount());
		System.out.println("线程:" + Thread.currentThread().getName() + ",已锁定数据行时长为:" + time);
		
		//加上一个运行时异常，看会怎么样(经过验证，运行时异常，锁会被释放掉)
		if("Thread-1".equals(Thread.currentThread().getName())) {
			throw new RuntimeException("运行时异常了.");
		}
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sFUDao.update(dbDto);
		System.out.println("线程:" + Thread.currentThread().getName() + ",已更新数据");
	}
	
	@Transactional
	@Override
	public void updateReturn(SFUDto udDto, boolean isReturn) {
		sFUDao.update(udDto);
		if(isReturn) {//return事务也是会提交的
			return ;
		}
	}
}
