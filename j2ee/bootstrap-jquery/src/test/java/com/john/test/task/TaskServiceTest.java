package com.john.test.task;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.john.task.service.TaskService;
import com.john.test.BasicTest;

public class TaskServiceTest extends BasicTest {
	@Autowired
	private TaskService taskService;
	
	@Test
	public void testSchedule() {
		taskService.updateAlreadyCount();
	}
}
