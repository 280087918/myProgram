package com.john.tools;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 这个测试类是为了使用工具类进行观测内存的回收状况的
 * 	可以使用jconsole或者jvisualvm进行观测，最好还是使用jconsole进行监测
 * 运行前使用下面jvm参数:
 * 	-Xms100m -Xmx100m -XX:+UseSerialGC
 * @author Administrator
 *
 */
public class ToolsOMMObj {
	public class OMMObj {
		public byte[] placeholder = new byte[64 * 1024];
	}
	
	void fillHeap(int num) throws InterruptedException {
		List<OMMObj> list = new ArrayList<OMMObj>();
		for (int i = 0; i < num; i++) {
			Thread.sleep(50);
			list.add(new OMMObj());
		}
		/**
		 * 因为gc的时候方法还没有退出，list引用还存在，所以这里gc并不能回收老年代的对象.放到(2)中可以回收
		 */
		//System.gc();
	}
	
	@Test
	public void testFill() {
		try {
			System.out.println("testFill() 开始......");
			Thread.sleep(10000);//睡10秒好有时间准备用工具链接这个程序
			fillHeap(1000);
			//(2)
			System.gc();
			System.out.println("testFill() 结束......");
			Thread.sleep(10000);//睡10秒再好好看下程序
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
