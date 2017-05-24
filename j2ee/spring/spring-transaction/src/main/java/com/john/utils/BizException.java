package com.john.utils;

import lombok.Data;

@Data
public class BizException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3855217714539537067L;
	
	protected String errorMsg;
	
	protected String errorCode;

	@Override
	public String getMessage() {
		return errorMsg;
	}
	
	public BizException(String errorMsg, String errorCode, Object ... objects) {
		super(String.format(errorMsg, objects));
        this.errorCode = errorCode;
        this.errorMsg = String.format(errorMsg, objects);
	}
}
