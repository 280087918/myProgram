package com.john.dao.book;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.john.vo.Book;

public interface BookDao {
	Logger log = LoggerFactory.getLogger(BookDao.class);
	
	//保存
	public void saveBook(Book book);
	
	//删除
	public void delDocBook(String docId);
	
	//移除索引
	public void clearIndex();
	
	//查询集合
	public List<Book> listBook(Book searcher);
}
