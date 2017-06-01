package com.john;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.john.vo.Account;

/**
 * 若是将spring context放到这里初始化的话，那么会启用多个线程各自new自己的spring context。达不到预期的效果
 * @author Administrator
 *
 */
public class MyTest extends AbstractJavaSamplerClient {
	private static Account account = new Account();

	@Override
	public void setupTest(JavaSamplerContext context) {
	}

	@Override
	public SampleResult runTest(JavaSamplerContext context) {
		System.out.println(Thread.currentThread().getName() + ":" + account.decr(1));
		SampleResult sr = new SampleResult();
		sr.sampleStart();
		sr.setResponseData("::::" + context.getIntParameter("count"), null);
		sr.setDataType(SampleResult.TEXT);
		
		sr.setSuccessful(true);
		sr.sampleEnd();
		return sr;
	}

}
