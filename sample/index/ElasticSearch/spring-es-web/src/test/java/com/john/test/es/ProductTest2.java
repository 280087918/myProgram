package com.john.test.es;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.john.dao.ProductRepository;
import com.john.test.BaseTest;
import com.john.vo.MyPageable;
import com.john.vo.Product;

public class ProductTest2 extends BaseTest {
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void save() {
		productRepository.save(new Product("017", "测试", "三星", 4468.00));
	}
	
	@Test
	public void update() {
		productRepository.save(new Product("017", "测试1", "三星", 4468.00));
	}
	
	@Test
	public void findByName() {
		List<Product> products = productRepository.findByName("三星");
		System.out.println("总共找到:(" + products.size() + ")条");
		System.out.println(products);
	}
	
	@Test
	public void countByName() {
		System.out.println(productRepository.countByName("三星"));
	}
	
	@Test
	public void page() {
		MyPageable pageable = new MyPageable();
		pageable.setPageNumber(1);
		pageable.setPageSize(2);
		Page<Product> page = productRepository.findByNameOrderBySalesPriceDesc(pageable, "三星");
		System.out.println(page.getContent());
	}
}
