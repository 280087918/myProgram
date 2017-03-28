package com.john.sfu.dao;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.john.dao.BaseDao;
import com.john.sfu.dto.SFUDto;

@BaseDao
public interface SFUDao {
	@Select("select id, amount from sfu where id=#{id} for update")
	@Results({
        @Result(id = true, property = "id", column = "id"),
        @Result(property = "amount", column = "amount"),
	})
	SFUDto selcetForUpdate(Integer id);
	
	@Update("update sfu set amount=#{amount} where id=#{id}")
	int update(SFUDto sfuDto);
}
