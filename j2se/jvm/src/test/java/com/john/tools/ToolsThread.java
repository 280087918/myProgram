package com.john.tools;

import org.junit.Test;
/**
 * 这个程序使用jconsle能看到线程也签中的loopThread线程一直挂在loop方法的15行
 * 	堆栈跟踪: 
 * 		com.john.tools.ToolsThread$1.run(ToolsThread.java:15)
		java.lang.Thread.run(Thread.java:745)
 * @author Administrator
 *
 */
public class ToolsThread {
//	private int i = 1;
	private void loop() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
//					try {
//						Thread.sleep(1000);
//						System.out.println("loop is runing......(" + i + ")");
//						i ++;
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
				}
			}
		}, "loopThread").start();
	}
	
	@Test
	public void testLoop() throws InterruptedException {
		loop();
		Thread.sleep(1000 * 60 * 5);//睡得足够长时间，这样好观测,不想看的时候就关闭程序就好了
	}
}
