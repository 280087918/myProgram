package com.john.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * @author Administrator
 *	本用例用以测试堆溢出的情况，因为List是强引用，所以内存并不能释放掉
 */
public class HeapOOM {
	private int count1 = 0;
	private int count2 = 0;
	
	//private double d1 = 0d;
	//private long l1 = 1l;
	
	public static void main(String[] args) {
		List<HeapOOM> list = new ArrayList<HeapOOM>();
		int i = 0;
		while(true) {
			System.out.println(i ++);
			list.add(new HeapOOM());
		}
	}
}
