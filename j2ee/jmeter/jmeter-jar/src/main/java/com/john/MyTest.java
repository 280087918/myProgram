package com.john;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.springframework.context.ApplicationContext;

import com.john.spring.SpringContext;

/**
 * 若是将spring context放到这里初始化的话，那么会启用多个线程各自new自己的spring context。达不到预期的效果
 * @author Administrator
 *
 */
public class MyTest extends AbstractJavaSamplerClient {
	ApplicationContext springContext = null;
	Component component = null;

	@Override
	public void setupTest(JavaSamplerContext context) {
		springContext = SpringContext.obtainContext();
		component = springContext.getBean(Component.class);
	}

	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		SampleResult sr = new SampleResult();
		sr.sampleStart();
		sr.setResponseData("::::" + component.hashCode(), null);
		sr.setDataType(SampleResult.TEXT);
		
		sr.setSuccessful(true);
		sr.sampleEnd();
		return sr;
	}

}
