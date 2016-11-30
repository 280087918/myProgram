package com.john.system.utils;

import java.security.MessageDigest;
import java.util.UUID;

import sun.misc.BASE64Encoder;

public class WebUtils {
	
	/**
	 * Md5加密
	 * 除null外，空串、空格等都可以生成md5字符串
	 * 如果eclipse无法识别BASE64Encoder,就设置project->Java Build Path->Libraries->JRE ....->双击Access rules
	 * 	添加Resolution:Accessible
	 * 	Rule Pattern:**
	 * @param str
	 * @return
	 */
	public static String strToMd5(String str) {
		if(null != str) {
			try {
				MessageDigest md5=MessageDigest.getInstance("MD5");
				BASE64Encoder base64en = new BASE64Encoder();
				//加密后的字符串
				return base64en.encode(md5.digest(str.getBytes("utf-8")));
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * 获取唯一id
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
