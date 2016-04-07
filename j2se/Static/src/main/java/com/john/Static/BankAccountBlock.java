package com.john.Static;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankAccountBlock {
	private final static Logger log = LoggerFactory.getLogger(BankAccountBlock.class);
	{
		log.info("静态快执行......");
	}
}
