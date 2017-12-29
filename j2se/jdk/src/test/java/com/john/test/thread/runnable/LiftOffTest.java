package com.john.test.thread.runnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.john.thread.runnable.LiftOff;

public class LiftOffTest {
	/**
	 * 这里就是启用了5个线程同时运行，通过LiftOff-->run方法里面的Thread.yield()进行cpu的时间碎片非配,说明本线程任务执行得差不多了，可以让把资源让给其他线程了
	 */
	@Test
	public void test1() {
		for (int i = 0; i < 5; i++)
			new Thread(new LiftOff()).start();
		System.out.println("waiting for LiftOff");
	}
	
	/**
	 * 适用java.util.concurent包的执行器(Executor)管理Thread对象。
	 * CachedThreadPool为每一个任务都提供一个线程
	 */
	@Test
	public void test2() {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++)
			exec.execute(new LiftOff());
		exec.shutdown();//shutdown方法可以方式新任务提交给Executor,证明线程组装到Executor就此结束
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 这个FixedThreadPool就是限定线程池的大小
	 * 	我这里设置大小为1可以看出来，当线程池没有多余的资源，其他线程任务将等待
	 */
	@Test
	public void test3() {
		ExecutorService exec = Executors.newFixedThreadPool(1);
		for (int i = 0; i < 5; i++)
			exec.execute(new LiftOff());
		exec.shutdown();//shutdown方法可以方式新任务提交给Executor,证明线程组装到Executor就此结束
		try {//不得不提，这里必须让主程序等待，要不然会将LiftOff当成后台线程处理，只要test3这个方法执行完了，就不会去管后台有没有线程了
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 这个等价于Executors.newFixedThreadPool(1);
	 * 	就是希望任务按照顺序去执行
	 */
	@Test
	public void test4() {
		ExecutorService exec = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 5; i++)
			exec.execute(new LiftOff());
		exec.shutdown();//shutdown方法可以方式新任务提交给Executor,证明线程组装到Executor就此结束
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 利用线程的setPiority来调整线程的优先级
	 * 	这样并不会导致死锁，只是表明优先级高的要比优先级低的执行频率更高
	 */
	@Test
	public void test5() {
		Thread t1 = new Thread(new LiftOff());
		t1.setPriority(Thread.MAX_PRIORITY);
		
		Thread t2 = new Thread(new LiftOff());
		t2.setPriority(Thread.MIN_PRIORITY);
		
		Thread t3 = new Thread(new LiftOff());
		t3.setPriority(Thread.MIN_PRIORITY);
		
		t1.start();
		t2.start();
		t3.start();
	}
	
	/**
	 * setDaemon是将线程设置为后台线程
	 * 	这里虽然看不出效果，但是理想的情况是t2会在后台之星，不会在控制台有输出
	 */
	@Test
	public void test6() {
		Thread t1 = new Thread(new LiftOff());
		Thread t2 = new Thread(new LiftOff());
		t2.setDaemon(true);
		
		t1.start();
		t2.start();
	}
}