package com.john.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * @author Administrator
 *	本用例用以测试堆溢出的情况，因为List是强引用，所以内存并不能释放掉
 */
public class HeapOOM {
	
	public static void main(String[] args) throws InterruptedException {
		List<HeapOOM> list = new ArrayList<HeapOOM>();
		Thread.sleep(10000);//用工具看一下堆的使用状况
		int i = 0;
		while(true) {
			System.out.println(i ++);
			list.add(new HeapOOM());
		}
	}
}
