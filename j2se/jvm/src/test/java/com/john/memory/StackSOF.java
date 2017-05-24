package com.john.memory;

/**
 * VM Args: -Xss128k
 * @author Administrator
 * 其实这个还真不好理解，在单个线程下，不停的有新方法压栈，所以导致超出预设的-Xss128k栈最大值
 * 而如果想在栈上得出OOM(OutOfMemoryError)的话，要创建足够多的线程去调用方法，导致栈不够空间分配线程执行的方法所到虚拟机栈上。
 */
public class StackSOF {
	private int stackLength = 1;
	
	public void addLength() {
		stackLength ++;
		addLength();
	}
	
	public static void main(String[] args) throws Throwable {
		StackSOF sof = new StackSOF();
		try {
			sof.addLength();
		} catch (Throwable e) {
			System.out.println(sof.stackLength);
			throw e;
		}
	}
}
