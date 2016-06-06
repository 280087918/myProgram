package com.john.file;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindFile {
	//http://www.cnblogs.com/lovebread/archive/2009/11/23/1609122.html
	Logger log = LoggerFactory.getLogger(FindFile.class);
	
	String fileName = "C:\\Program Source\\ydp-background";
	
	@Test
	public void read() {
		File file = new File(fileName);
		log.info(":" + file.isDirectory());
	}
}
