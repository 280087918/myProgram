package com.john.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


/**
 * spring异常统一处理，实现spring接口的方式
 * @author zhang.hc
 * @date 2016年5月13日 上午11:34:13
 */
public class JExceptionHandler implements HandlerExceptionResolver {
	private Logger log = LoggerFactory.getLogger(JExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		log.info("捕获了异常信息....");
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        PrintStream ps = new PrintStream(baos);
        ex.printStackTrace(ps);
        
		Map<String, Object> model = new HashMap<String, Object>();
		//log.error(baos.toString());
		model.put("ex", baos.toString());
		
		return new ModelAndView("exceptions/error_page", model);
	}
}
