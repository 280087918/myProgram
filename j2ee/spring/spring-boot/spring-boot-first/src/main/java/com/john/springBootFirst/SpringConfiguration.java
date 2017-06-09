package com.john.springBootFirst;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.john.springBootFirst.system.Config;

/**
 * 按照spring-boot的规范，@EnableAutoConfiguration类应该放到项目的root目录下
 * 	而root目录应该参照java的命名规范:com.domainName.project(以后所有的demo项目都要严格按照这个规范创建)
 * 要不然ComponentScan等将扫不到包。
 * @author Administrator
 *
 */
@Configuration//声明为配置类
@ComponentScan//自动扫包
@EnableAutoConfiguration
@EnableConfigurationProperties({Config.class})
public class SpringConfiguration {
}
