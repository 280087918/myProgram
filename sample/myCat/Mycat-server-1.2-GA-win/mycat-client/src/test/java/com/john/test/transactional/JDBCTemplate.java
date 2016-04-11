package com.john.test.transactional;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.john.test.BaseTest;

/**
 * MyCat不支持事务回滚，好比说，我们这里JDBCTemplate就没有事务控制
 * 	1.插入一条id为300的数据
 *  2.然后从id为1一直到500的批量插入
 *  3.到300的时候数据库异常了，300之前的数据因为没有事务机制，所以不会滚，还是在数据库里面了
 * @author zhang.hc
 * @date 2016年4月11日 下午5:30:45
 */
public class JDBCTemplate extends BaseTest {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void delUsers() {
		jdbcTemplate.execute("delete from `t_user_ext`");
	}
	
	@Test
	public void save300() {
		jdbcTemplate.execute("INSERT INTO `t_user_ext`(`user_id`,`receive_address`,`create_time`,`province_code`) VALUES ('300', '广州市越秀区广州大道中599号', '2014-07-17 10:53:15', 'GD')");
	}
	
	@Test
	public void test1() {
		List<String> sqls = new ArrayList<String>();
		for (int i = 1; i <= 500; i++) {
			sqls.add("INSERT INTO `t_user_ext`(`user_id`,`receive_address`,`create_time`,`province_code`) VALUES ('" + i + "', '广州市越秀区广州大道中599号', '2014-07-17 10:53:15', 'GD')");
		}
		
		for (String sql : sqls) {
			jdbcTemplate.execute(sql);
		}
	}
}
