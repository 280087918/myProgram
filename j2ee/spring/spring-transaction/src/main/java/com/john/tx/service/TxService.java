package com.john.tx.service;

import java.util.List;

import com.john.tx.dto.TxDto;
import com.john.utils.BizException;

public interface TxService {
	
	void save(TxDto txDto);
	
	List<TxDto> findList();
	
	public void updateLongTime(TxDto txDto, long time);
	
	public void updateWithTrans(TxDto txDto) throws BizException;
}
