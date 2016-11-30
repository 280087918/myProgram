package com.john.test.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.task.dao.TaskMapper;
import com.john.task.vo.TaskVo;
import com.john.test.BasicTest;

public class TaskDaoTest extends BasicTest {
	@Autowired
	private TaskMapper taskMapper;
	
	@Test
	public void testFind() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("createUser", "zhanghc");
		params.put("deleteFlag", "0");
		List<TaskVo> taskVos = taskMapper.findTasks(params);
		logger.info("{}", taskVos);
	}
}
