package com.john.spring_boot_web;

import org.springframework.boot.SpringApplication;

/**
 * 这里将启动类不放到test里面，因为是想通过mvn命令直接启动容器
 * 	cmd到工程目录下，执行mvn spring-boot:run,可以自动探测项目上的main方法，并且执行它
 * @author Administrator
 */
public class Application {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SpringConfiguration.class);
		app.run(args);
	}
}