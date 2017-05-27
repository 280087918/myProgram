package com.john.test.user;

import javax.annotation.Resource;
import javax.validation.Validator;

import org.junit.Test;

import com.john.test.BaseTest;
import com.john.utils.ValidationUtil;
import com.john.vo.User;

public class UserValidationTest extends BaseTest {
	@Resource(name="validator")
	private Validator validator;
	
	@Test
	public void test1() {
		User user = new User();
		user.setUserName("zhanghc");
		ValidationUtil.validateWithException(validator, user);
	}
}
