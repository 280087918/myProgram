package com.john.springBootFirst;

import org.springframework.boot.SpringApplication;

/**
 * 这里将启动类不放到test里面，因为是想通过mvn命令直接启动容器
 * 	cmd到工程目录下，执行mvn spring-boot:run,可以自动探测项目上的main方法，并且执行它
 * @author Administrator
 */
public class Application {
	public static void main(String[] args) {
		//System.setProperty("spring.devtools.restart.enabled", "false");//关闭class修改的monitor
		//SpringApplication.run(SpringConfiguration.class, args);
		
		SpringApplication app = new SpringApplication(SpringConfiguration.class);
		//app.setBannerMode(Banner.Mode.OFF);//可以关闭banner
		app.run(args);
	}
}
/*
 *这个也是简化代码的一个手段，相当于@Configuration @EnableAutoConfiguration @ComponentScan。然而个人觉得反而所有都大同了，反而不合适
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}*/
