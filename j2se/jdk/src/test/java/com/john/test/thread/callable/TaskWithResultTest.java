package com.john.test.thread.callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import com.john.thread.callable.TaskWithResult;

public class TaskWithResultTest {
	/**
	 * 如果想让线程执行完之后返回一个结果，那么就需要用Callable接口了
	 */
	@Test
	public void test1() {
		ExecutorService exec = Executors.newCachedThreadPool();
		List<Future<String>> results = new ArrayList<Future<String>>();
		for (int i = 1; i <= 10; i++)
			//1.ExecutorService的submit方法会返回一个Future对象
			results.add(exec.submit(new TaskWithResult(i)));
		for(Future<String> result : results) {
			try {
				//2.Future对象里面可以直接用get获取返回值，不过在这之前可以适用isDone方法来确定线程是否完成
				System.out.println(result.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
}
