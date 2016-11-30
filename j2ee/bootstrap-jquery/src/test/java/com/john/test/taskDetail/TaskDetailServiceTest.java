package com.john.test.taskDetail;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.task.service.TaskService;
import com.john.task.vo.TaskVo;
import com.john.taskDetail.service.TaskDetailService;
import com.john.test.BasicTest;

public class TaskDetailServiceTest extends BasicTest {
	@Autowired
	private TaskDetailService taskDetailService;
	
	@Autowired
	private TaskService taskService;

	@Test
	public void test() {
		//任务信息
		Map<String, Object> taskParams = new HashMap<String, Object>();
		taskParams.put("createUser", "zhanghc");
		taskParams.put("currentDate", DateTime.now().toDate());
		TaskVo taskVo = taskService.obtainCurrenTask(taskParams);
		if(null != taskVo) {
			System.out.println(taskDetailService.obtainSignPercentage(taskVo));
		} else {
			System.out.println("毛都没有");
		}
	}
}
