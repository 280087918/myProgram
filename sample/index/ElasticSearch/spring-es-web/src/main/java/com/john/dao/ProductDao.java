package com.john.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.john.vo.Product;

public interface ProductDao {
	Logger log = LoggerFactory.getLogger(ProductDao.class);
	
	/**
	 * 存储单个商品
	 * @param product
	 */
	public void saveProduct(Product product);
	
	/**
	 * 批量存储商品
	 * @param products
	 */
	public void batchSaveProduct(List<Product> products);
	
	/**
	 * 搜索提示
	 * @param keyword
	 */
	public List<String> searchTips(String keyword);
	
	/**
	 * 搜索集合
	 * @param keyword
	 * @return
	 */
	public List<Product> searchList(String keyword);
}
