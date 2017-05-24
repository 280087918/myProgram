package com.john.tx.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.john.dao.BaseDao;
import com.john.tx.dto.TxDto;

@BaseDao
public interface TxDao {
	@Insert("insert into tx (`id`,`num`,`version`) values (#{id}, #{num}, #{version})")
	void save(TxDto txDto);
	
	@Select("select id, num, version from tx")
	@Results({
        @Result(id = true, property = "id", column = "id"),
        @Result(property = "num", column = "num"),
        @Result(property = "version", column = "version")
	})
	List<TxDto> queryList();
	
	@Delete("delete from tx where 1=1")
	void clear();
	
	@Update("update tx set num=#{num} where id=#{id} and version=#{version}")
	int update(TxDto txDto);
	
	//这个是与事务无关的测试，主要测试如何用这种注解的方式去返回Map对象的
	@Select("select id, num, version from tx")
	@Results({
        @Result(id = true, property = "jid", column = "id"),
        @Result(property = "jnum", column = "num"),
        @Result(property = "jversion", column = "version")
	})
	List<Map<String, Object>> queryMapList();
}
