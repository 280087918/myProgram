package com.john.dao.impl.car;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Field;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
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
	
	@Override
	public PageVo<Car> queryPage(PageVo<Car> page, Car searcher) {
//		SearchRequestBuilder docSearcher
//		QueryBuilder qb = queryStringQuery("");
//		
//		SearchQuery query = new NativeSearchQueryBuilder()
////			.withQuery(queryBuilder)
//			.build();
//		elasticsearchTemplate.queryForList(query, Car.class);
		
		Criteria criteria = new Criteria();
		Field field = null;
		
		if(StringUtils.isNotBlank(searcher.getBrandName())) {//要是品牌名称不为空的话
			field = new SimpleField("brandName");
			criteria = criteria.and(field);
			log.info("::" + criteria.toString());
		}
		
		CriteriaQuery query = new CriteriaQuery(criteria);
		List<Car> cars = elasticsearchTemplate.queryForList(query, Car.class);
		log.info("" + cars);
		return null;
	}
}
