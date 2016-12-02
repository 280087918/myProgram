package com.john.test.taskDetail;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.system.utils.ResultVo;
import com.john.task.service.TaskService;
import com.john.task.vo.TaskVo;
import com.john.taskDetail.service.TaskDetailService;
import com.john.test.BasicTest;
import com.john.user.vo.UserVo;

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
	
	@Test
	public void testSign() {
		//任务信息
		UserVo userVo = new UserVo();
		userVo.setName("zhanghc");
		ResultVo resultVo = taskDetailService.sign(userVo);
		System.out.println(resultVo);
	}
	
	@Test
	public void testClear() {
		//任务信息
		UserVo userVo = new UserVo();
		userVo.setName("zhanghc");
		ResultVo resultVo = taskDetailService.clear(userVo);
		System.out.println(resultVo);
	}
}
