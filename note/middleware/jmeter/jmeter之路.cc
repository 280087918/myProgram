工具背景:
	用来模拟线程去给相关的接口，http请求等做压力测试，并且生成相关报告。

参考文档:
http://www.cnblogs.com/fnng/archive/2012/12/29/2839420.html

1.先从官网下载下来，因为3.2说是至少运行在jdk8之上，我也没有比对之前的版本都有什么关系，所以随便下了个2.8版本的.
  apache-jmeter-2.8.zip，简单的操作可以直接运行bin目录下的jmeter.bat进行压力策略的配置和执行

2.jmeter可以使用的场景
  a)http、https请求
  b)SOAP
  c)数据库(只能通过JDBC)
  d)LDAP
  e)JMS
  f)本地命令或者shell脚本

3.一个简单的web测试
  本次直接测试我git上的spring-mvc项目
  a)打开jmeter.bat
  b)测试计划(右键)->添加->Threads(Users)->添加线程组
	名称:线程组1
	线程数:20 (并发线程数)
	Ramp-Up Period(in seconds):0
	循环次数:5
  c)执行计划->线程组1(右键)->添加->配置元件->HTTP Cookie管理器
	有些网站需要记录cookie，这里貌似直接新建这个cookie管理器即可
  d)执行计划->线程组1(右键)->添加->Sampler->HTTP请求
	服务器名称:localhost
	端口:80
	路径:index.sc
  e)执行计划->线程组1(右键)->添加->监听器->查看结果树
  f)直接点运行

4.java程序压力测试
  不用考虑导出可执行jar包的问题
  a)pom文件里面引用JMeter的开发包
  <dependency>  
      <groupId>org.apache.jmeter</groupId>
      <artifactId>ApacheJMeter_java</artifactId>
      <version>2.8</version>
  </dependency>
  b)新建一个类继承AbstractJavaSamplerClient
     在runTest里面写实现代码
     这里有个问题需要注意:就是在这个类里面，相当于是线程单元，所以在编写的代码可以认为是线程私有的。
     最明显的就是在里面初始化spring context，那么就是一个线程初始化一个context。而且这些context是相互独立的
  c)直接mvc clean package打包即可
  d)将导出的jar包放到jmeter的lib/ext目录下，如果jar包有需要引用的第三方jar包，那么那些第三方jar包都要放到lib目录下
  e)JMeter新建测试计划(test-plan)
  f)新建线程组(req-threads)
  g)新建java请求,选中继承了AbstractJavaSamplerClient的这个类
  h)新建查看结果数

  如果需要接受参数，比如需要传入一个随机数,那么就在"Java请求"页签上添加参数，参数名为:count,值为${__Random(1,10)}。生成1-10之间的随机数
  程序里面，runTest的重写方法里面这么获取就可以了.context.getIntParameter("count")
  JMeter可视化界面中就有一个"函数助手"可以帮忙生成函数。

5.dubbo压力测试
  如果实验性质的，我觉得一个线程new 一个ClassPathXmlApplicationContext出来也是没有问题的。因为我们最终不是要压这个jar包，只是要压测实现端。
  不过如果线程数比较多的情况下，可能要加大JMeter的jvm内存。或者在jar包里面写一个单例获取ApplicationContext对象

6.无UI压测
  linux上安装jmeter，直接上传网上下载的zip包也行，因为里面就有sh脚本，新增环境变量
  vim /etc/profile
	添加export PATH=/usr/local/apache-jmeter-2.8/bin:$PATH
  如果上述的案例都能顺利压测，那么就可以将测试计划另存为一个jmx的xml结构文件，以后就在那基础上进行修改扩展成其他测试计划即可
	注意，这里另存为的时候一定要选择测试计划这个根进行另存为，要不然会出错。
  jmeter -n -t dubbo-test-plan.jmx -l dubbo-test-plan.jtl
  名词解释:n:nongui模式， t:需要执行的脚本名称 , l:输出的日志

  查看jtl文件
  more dubbo-test-plan.jtl
  <sample t="103" lt="0" ts="1495437617918" s="true" lb="Java Request" rc="" rm="" tn="theads-group 1-1" dt="text" by="14"/>
  t:请求的时长，s:表示返回结果是成功与否,tn:线程名称,by:请求和响应字节数

7.jmeter3.2(需要jdk1.8环境)
  jmeter -n -t my-webpost-plan.jmx -l my-webpost-plan.jtl -e -o webpost_report
  -e -o是新版本特有的，因为jmx文件里面指定了生成聚合报告，所以这个意思是将聚合报告输出到指定目录webpost_report
  jmeter2.8是不具备这种特性
  输出的文件夹可以拉到本地用浏览器打开进行观察