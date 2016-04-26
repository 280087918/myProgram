package com.john.test.book;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.dao.book.BookDao;
import com.john.test.BaseTest;
import com.john.vo.Book;

/**
 * 主要是分词器的测试，这里是用ik分词器里面的一种ik_smart
 * 分词效果:
 * POST http://localhost:9200/_analyze?analyzer=ik_smart&text=环游世界一百天
 * @author zhang.hc
 * @date 2016年4月26日 下午3:30:02
 */
public class BookTest extends BaseTest {
	@Autowired
	private BookDao bookDao;
	
	@Test
	public void testSave() {
		Book book = new Book("bk001", "环游世界一百天", "jp001", DateTime.now().toDate());
		System.out.println(book.toString());
		
		bookDao.saveBook(book);
	}
	
	@Test
	public void testDel() {
		bookDao.delDocBook("bk001");
	}
	
	@Test
	public void testClearIndex() {
		bookDao.clearIndex();
	}
	
	@Test
	public void testList() {
		Book searcher = new Book();
		searcher.setName("环游世界");
		bookDao.listBook(searcher);
	}
}
