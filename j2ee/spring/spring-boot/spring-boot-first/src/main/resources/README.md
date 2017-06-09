这个项目用来放置或者测试spring-boot的基础特性
1.可以更换启动时console输出的banner
      在class-path下面新建一个banner.txt即可
      文字输出美化:http://patorjk.com/software/taag-v1/
      
2.jar启动
	如果启动的时候想看debug信息那么就:java -jar xxxx.jar --debug
	更改端口java -jar xxxx.jar --name="Spring" --server.port=8081
	
3.application.properties
  spring-boot默认从这里面读取配置，如果是spring-boot内部的变量，便会按照这里面的设置赋值进去
      比如我在文件里设置了server.port=8082，那么spring-boot启动起来之后的端口号是8082
      这个文件可以放到classpath下，或者放在classpath的config目录下，spring-boot会自动加载
      这种配置可以用YAML(yml)文件替代

4.YAML是json的超集,最终将文件内容转换成跟properties一样的数据格式

5.Profile的3种指定方法
  a)在@Configuration的POJO中指定@Profile("product")
  b)在properties文件中声明:spring.profiles.active=dev,hsqldb
  c)在启动命令中指定--spring.profiles.active=dev,hsqldb
  profile跟一般的属性一样遵循一个规则，可以在配置文件中指定profile， 然后在启动命令中再次指定profile，那么将使用启动命令中的profile
  
6.在Starters模式下，默认是用Logback的，所以我这里放了Logback的配置文件马上就生效了
      可以在jar包运行的时候指定日志的输出级别:java -jar spring-boot-first-0.0.1-SNAPSHOT.jar --debug
      
7.logback-spring.xml
      用这个替代传统的logback.xml是因为spring在上面做了profile的支持，可以根据不同的profile输出不同的日志级别
