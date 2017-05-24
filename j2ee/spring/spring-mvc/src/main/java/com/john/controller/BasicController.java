package com.john.controller;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BasicController {
	protected Logger log = LoggerFactory.getLogger(BasicController.class);
	
	/**
	 * spring异常统一处理，Annotation的方式
	 * @author zhang.hc
	 * @date 2016年5月13日 下午2:32:25
	 */
	@ExceptionHandler//关键点
	public String exception(HttpServletRequest request, Exception ex) {
		log.info("捕获异常Annotation......");
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        PrintStream ps = new PrintStream(baos);
        ex.printStackTrace(ps);//将异常信息输出到字节留当中
        
        request.setAttribute("ex", baos.toString());
        
		return "exceptions/error_page";
	}
}
