package com.john.utils;

import java.lang.reflect.Method;

/**
 * 用来做高并发的实验
 * @author jonathan.Chang
 *
 */
public class ThreadsUtils {
	/**
	 * 根据入参组装线程组
	 * @return
	 */
	public static Thread[] obtainThreads(final Object obj,final String methodName, int threadNums, final Object ... params) {
		Thread[] threads = new Thread[threadNums];
		for (int i = 0; i < threadNums; i++) {
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Class<?>[] paramTypes = null;
						//判断一下有没有传过来参数
						if(params.length > 0) {
							paramTypes = new Class<?>[params.length];
							for (int j = 0; j < params.length; j++) {
								paramTypes[j] = params[j].getClass();
							}
						}
						Method method = obj.getClass().getDeclaredMethod(methodName, paramTypes);
						method.invoke(obj, params);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		return threads;
	}
	
	public static void runThreads(Thread[] threads) {
		for(Thread thread : threads) {
			thread.start();
		}
	}
}
