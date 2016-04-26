package com.john.dao.impl.book;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import com.john.dao.book.BookDao;
import com.john.vo.Book;

@Repository
public class BookDaoImpl implements BookDao {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@PostConstruct
	public void init() {
		if(!elasticsearchTemplate.indexExists(Book.class)) {
			elasticsearchTemplate.createIndex(Book.class);
		}
		
		elasticsearchTemplate.putMapping(Book.class);
		log.info("--" + elasticsearchTemplate.getMapping(Book.class));
	}
	
	@Override
	public void saveBook(Book book) {
		IndexQuery index = new IndexQueryBuilder().withId(book.getId()).withObject(book).build();
		String result = elasticsearchTemplate.index(index);
		log.info("存储结果:" + result);
	}
	
	@Override
	public void delDocBook(String docId) {
		elasticsearchTemplate.delete(Book.class, docId);
	}
	
	@Override
	public void clearIndex() {
		elasticsearchTemplate.deleteIndex(Book.class);
	}
	
	@Override
	public List<Book> listBook(Book searcher) {
		
		SearchQuery searchQuery = null;
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
		
		StringBuffer sb = new StringBuffer();
		
		HighlightBuilder.Field[] highlightFields = new HighlightBuilder.Field[1];//高亮域
		
		if(StringUtils.isNotBlank(searcher.getName())) {
			sb.append(" +name:" + searcher.getName());
			HighlightBuilder.Field field = new HighlightBuilder.Field("name").preTags("<font color='red'>").postTags("</font>");
			highlightFields[0] = field;
		}
		
		queryBuilder.withQuery(queryStringQuery(sb.toString()).analyzer("ik_smart")).withHighlightFields(highlightFields);
		searchQuery = queryBuilder.build();
		log.info(sb.toString());
		
		Page<Book> bookPage = elasticsearchTemplate.queryForPage(searchQuery, Book.class, new SearchResultMapper() {
			
			@SuppressWarnings("unchecked")
			@Override
			public <T> Page<T> mapResults(SearchResponse response, Class<T> clazz,
					Pageable pageable) {
				List<Book> books = new ArrayList<Book>();
				Book book = null;
				for(SearchHit sh :response.getHits()) {
					if(response.getHits().getHits().length <= 0) {
						return null;
					}
					book = new Book();
					book.setId(sh.getId());
					//book.setName((String) sh.getSource().get("name"));
					book.setName(sh.getHighlightFields().get("name").fragments()[0].toString());
					book.setCode((String) sh.getSource().get("code"));
					book.setPublishDate(new Date((Long) sh.getSource().get("publishDate")));
					books.add(book);
				}
				if(CollectionUtils.isNotEmpty(books)) {
					return new PageImpl<T>((List<T>) books);
				}
				return null;
			}
		});
		log.info("page:" + bookPage.getContent());
		
//		List<Book> list = elasticsearchTemplate.queryForList(searchQuery, Book.class);
//		log.info("result:" + list);
		return null;
	}
}
