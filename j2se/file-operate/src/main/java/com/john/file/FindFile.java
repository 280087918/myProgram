package com.john.file;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 在路径fileName下查找所有包含keywords文本的文件，并记录详细路径
 * @author zhang.hc
 * @date 2016年6月7日 上午10:07:11
 */
public class FindFile {
	//http://www.cnblogs.com/lovebread/archive/2009/11/23/1609122.html
	Logger log = LoggerFactory.getLogger(FindFile.class);
	
	String fileName = "F:\\Program Source\\ydp-mall";
	Set<String> paths = new HashSet<String>();
	
	String keywords = "com.rabbitmq";
	
	@Test
	public void read() {
		File root = new File(fileName);
		log.info(":" + root.isDirectory());
		
		readDir(root);
		log.info("搜索关键词:{}", keywords);
		log.info("size:{}", paths.size());
		for(String path : paths) {
			log.info("请查阅:{}", path);
		}
	}
	
	private void readDir(File node) {//读取目录
		File[] files = node.listFiles();
		for (int i = 0; i < files.length; i++) {
			if(files[i].isDirectory()){//如果是文件夹的话，继续往下递归
				readDir(files[i]);
			} else {
				Scanner in = null;
				try {
					in = new Scanner(files[i]);
					
					while (in.hasNextLine()) {
						String str = in.nextLine();
						if(null != str) {
							if(str.indexOf(keywords) >= 0) {
								paths.add(files[i].getAbsolutePath());
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if(null != in) {
						in.close();
					}
				}
			}
		}
	}
}
	