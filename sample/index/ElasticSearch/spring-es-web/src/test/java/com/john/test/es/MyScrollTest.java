package com.john.test.es;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.dao.MyScrollDao;
import com.john.test.BaseTest;
import com.john.vo.MyScroll;

/**
 * 这个主要是为了实现ES的scroll分页效果
 * 	这种搜索也可以排序
 *  这种分页有一个特点，只能往后翻，因为这个线程的scrollId一旦生成了之后，可以一直使用这个id，并且拿出相应的结果集
 *  所以这个还是不适用与前台分页功能
 * @author zhang.hc
 * @date 2016年9月6日 下午2:58:38
 */
public class MyScrollTest extends BaseTest {
	@Autowired
	private MyScrollDao myScrollDao;
	
	@Test
	public void saveScrolls() {
		for (int i = 1; i <= 1000; i++) {
			myScrollDao.saveObject(new MyScroll("" + i, "name_" + i, i));
		}
	}
	
	@Test
	public void listTest() {
		myScrollDao.searchList("what ever.");
	}
}
