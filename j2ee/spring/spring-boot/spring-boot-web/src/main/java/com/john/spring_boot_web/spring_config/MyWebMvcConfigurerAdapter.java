package com.john.spring_boot_web.spring_config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 视图配置器(这个目前还没有搞清楚，先不用)
 * @author Jonathan.Chang
 *
 */
//@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 registry.addResourceHandler("/**").addResourceLocations("classpath:/static_resources/");
		super.addResourceHandlers(registry);
	}
}