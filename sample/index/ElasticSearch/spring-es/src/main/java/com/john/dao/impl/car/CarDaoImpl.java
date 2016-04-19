package com.john.dao.impl.car;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.search.sort.SortBuilders.fieldSort;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Field;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.SimpleField;
import org.springframework.stereotype.Repository;

import com.john.base.vo.PageVo;
import com.john.dao.car.CarDao;
import com.john.vo.Car;

@Repository
public class CarDaoImpl implements CarDao {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Override
	public void saveDocCar(Car car) {
		IndexQuery index = new IndexQuery();
		index.setId(car.getId());//设置索引文件id
		index.setObject(car);
		
		elasticsearchTemplate.index(index);
	}
	
	@Override
	public void delDocCar(String docId) {
		elasticsearchTemplate.delete(Car.class, docId);
	}
	
	@Override
	public void saveBatchDocCar(List<Car> cars) {
		IndexQuery index;
		List<IndexQuery> queries = new ArrayList<IndexQuery>();
		if(null != cars && cars.size() > 0) {
			for(Car car : cars) {
				index = new IndexQuery();
				index.setId(car.getId());
				index.setObject(car);
				queries.add(index);
			}
		}
		if(null != queries && queries.size() > 0) {
			elasticsearchTemplate.bulkIndex(queries);
		}
	}
	
	//这个只能查询一个条件，Criteria里面的chain没有起作用
	public PageVo<Car> queryPage1(PageVo<Car> page, Car searcher) {
//		SearchRequestBuilder docSearcher
//		QueryBuilder qb = queryStringQuery("");
//		
//		SearchQuery query = new NativeSearchQueryBuilder()
////			.withQuery(queryBuilder)
//			.build();
//		elasticsearchTemplate.queryForList(query, Car.class);
		
		Criteria criteria = null;//单个查询条件组装
		Field field = null;//单个查询条件的查询域
//		CriteriaQuery query = null;//查询条件汇总
		
		
		if(StringUtils.isNotBlank(searcher.getAreaType())) {//要是品牌名称不为空的话
			field = new SimpleField("areaType");
			criteria = Criteria.where(field).contains(searcher.getAreaType());
		}
		
		if(StringUtils.isNotBlank(searcher.getBrandName())) {
			field = new SimpleField("brandName");
			criteria = criteria.and(field).greaterThan(searcher.getBrandName());
		}
		
		if(null != searcher.getProdDateBegin()) {//时间大于起始时间
			field = new SimpleField("prodDate");
			criteria = criteria.and(field).greaterThan(searcher.getProdDateBegin());
		}
		
		CriteriaQuery query = new CriteriaQuery(criteria);
		List<Car> cars = elasticsearchTemplate.queryForList(query, Car.class);
		log.info("" + cars);
		return null;
	}
	
	//可以了，跟之前的版本不一样，这里用+号
	public PageVo<Car> queryPage(PageVo<Car> page, Car searcher) {
		SearchQuery searchQuery = null;
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
		
		StringBuffer sb = new StringBuffer();
		
		if(StringUtils.isNotBlank(searcher.getAreaType())) {
			sb.append(" +areaType:" + searcher.getAreaType());
			//sb.append(" +areaType:欧美,德国"); //这相当于or条件查询
		}
		
		if(StringUtils.isNotBlank(searcher.getBrandName())) {
			sb.append(" +brandName:" + searcher.getBrandName());
		}
		queryBuilder.withQuery(queryStringQuery(sb.toString()));
		log.info(sb.toString());
		
		//---范围搜索
		if(null != searcher.getProdDateBegin()) {//时间大于起始时间
			queryBuilder.withFilter(rangeQuery("prodDate").from(searcher.getProdDateBegin().getTime()));
		}
		if(null != searcher.getProdDateEnd()) {//结束时间搜索
			queryBuilder.withFilter(rangeQuery("prodDate").to(searcher.getProdDateEnd().getTime()));
		}
		
		//排序在这里
		queryBuilder.withSort(fieldSort("prodDate").order(SortOrder.DESC));
		
		//分页在这里,下标要从0开始
		Pageable pageable = new PageRequest(page.getPageIndex() - 1, page.getPageSize());
		queryBuilder.withPageable(pageable);
		
		searchQuery = queryBuilder.build();
//		List<Car> cars = elasticsearchTemplate.queryForList(searchQuery, Car.class);
//		log.info("" + cars);
		
		Page<Car> respPage = elasticsearchTemplate.queryForPage(searchQuery, Car.class);
		log.info("一共有这么多符合条件的结果:" + respPage.getTotalElements());
		log.info("一共有这么多页:" + respPage.getTotalPages());
		log.info("数据:" + respPage.getContent());
		
		return null;
	}
	
	//这个搜索中文会有问题，只能过滤一个中文字
	public PageVo<Car> queryPage3(PageVo<Car> page, Car searcher) {
		SearchQuery searchQuery = null;
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
		
		BoolQueryBuilder boolQueryBuilder = boolQuery();
		if(StringUtils.isNotBlank(searcher.getAreaType())) {
			boolQueryBuilder.must(termQuery("areaType", searcher.getAreaType()));
		}
		
		if(StringUtils.isNotBlank(searcher.getBrandName())) {
			boolQueryBuilder.must(termQuery("brandName", searcher.getBrandName()));
		}
		
		queryBuilder.withQuery(boolQueryBuilder);
		searchQuery = queryBuilder.build();
		List<Car> cars = elasticsearchTemplate.queryForList(searchQuery, Car.class);
		log.info("" + cars);
		return null;
	}
}
