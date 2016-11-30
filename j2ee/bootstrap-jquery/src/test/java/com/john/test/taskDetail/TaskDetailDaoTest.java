package com.john.test.taskDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
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
	
	@Test
	public void testFindCurrentLastSign() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskId", "d06375d6cf194127ac9c7d553a556ced");
		DateTime dt = new DateTime("2016-11-30T00:00:00");
		params.put("currentDate", dt.toDate());
		TaskDetailVo taskDetailVo = taskDetailMapper.findCurrentLastUnsignDetail(params);
		logger.info("{}", taskDetailVo);
	}
}
