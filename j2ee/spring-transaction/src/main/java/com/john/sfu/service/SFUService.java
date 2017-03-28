package com.john.sfu.service;

import com.john.sfu.dto.SFUDto;

public interface SFUService {
	public void update(SFUDto udDto, long time);
	
	public void updateReturn(SFUDto udDto, boolean isReturn);
}
