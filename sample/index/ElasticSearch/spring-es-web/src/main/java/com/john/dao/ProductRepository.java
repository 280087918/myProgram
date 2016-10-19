package com.john.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.john.vo.Product;

/**
 * 这是使用springdata接口进行curd的操作
 * @author zhang.hc
 * @date 2016年8月10日 下午6:16:27
 */
//public interface ProductRepository extends PagingAndSortingRepository<Product, String> {
public interface ProductRepository {
	
//	@Query("{\"bool\" : {\"should\" : [{\"term\" : {\"name\" : \"?0\"}}, {\"term\" : {\"description\" : \"?0\"}}]}}}")
//	@Query("{\"bool\" : {\"should\" : [{\"term\" : {\"name\" : \"?0\"}}]}}}")
	List<Product> findByName(String query);
	
	Long countByName(String name);
	
	Page<Product> findByNameOrderBySalesPriceDesc(Pageable pageable, String name);
}
