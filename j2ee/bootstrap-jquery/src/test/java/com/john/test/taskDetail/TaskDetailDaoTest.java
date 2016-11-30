package com.john.test.taskDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.taskDetail.dao.TaskDetailMapper;
import com.john.taskDetail.vo.TaskDetailVo;
import com.john.test.BasicTest;

public class TaskDetailDaoTest extends BasicTest {
	@Autowired
	private TaskDetailMapper taskDetailMapper;
	
	@Test
	public void testFind() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("signUser", "zhanghc");
		params.put("isSign", 1);
		List<TaskDetailVo> taskDetailVos = taskDetailMapper.findTaskDetails(params);
		logger.info("{}", taskDetailVos);
	}
}
