package com.john.Static;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankAccountStatic {
	private final static Logger log = LoggerFactory.getLogger(BankAccountStatic.class);
	static{
		log.info("static块执行......");
	}
}
