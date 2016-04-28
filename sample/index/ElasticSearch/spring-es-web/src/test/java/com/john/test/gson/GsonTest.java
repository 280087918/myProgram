package com.john.test.gson;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class GsonTest {
	Logger log = LoggerFactory.getLogger(GsonTest.class);
	
	@Test
	public void test1() {
		List<String> list = new ArrayList<String>();
		list.add("aa");
		list.add("bb");
		list.add("cc");
		Gson gson = new Gson();
		log.info(gson.toJson(list));
	}
}
