package com.john.consumer.run;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.john.api.AreaApi;

public class AreaApiRun extends AbstractJavaSamplerClient {
	private ApplicationContext springContext = null;
	private AreaApi areaApi = null;
	
	@Override
	public void setupTest(JavaSamplerContext context) {
		super.setupTest(context);
		if(null == springContext) {
			try {
				springContext = new ClassPathXmlApplicationContext("dubbo-consumer.xml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(null == areaApi)
			areaApi = springContext.getBean(AreaApi.class);
	}

	@Override
	public SampleResult runTest(JavaSamplerContext context) {
		SampleResult sr = new SampleResult();
		sr.sampleStart();
		
		areaApi.listAreas(context.getIntParameter("count"));
		
		sr.setResponseData("::::" + areaApi.hashCode(), null);
		sr.setDataType(SampleResult.TEXT);
		
		sr.setSuccessful(true);
		sr.sampleEnd();
		return sr;
	}

}
