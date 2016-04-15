package com.john.repositorys;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.john.vo.User;

public interface UserRepository extends ElasticsearchRepository<User, String> {
	
}
