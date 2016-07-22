package com.john.utils;

import java.util.UUID;

public class IdUtils {
	/**
	 * 获取唯一id
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
