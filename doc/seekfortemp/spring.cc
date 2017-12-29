1.struts2
  web.xml里面配置Struts2的核心控制器，用来拦截客户端请求，并将请求转发给相应的Action类来处理。
  struts2是用web容器的filter进行拦截的。拦截的类是struts2包下的dispatcher.FilterDispatcher.
  使用的过程是在struts.xml里面配置好类以及别名的映射关系，前端访问资源的时候就是别名+方法名的方式进行访问.

2.springmvc
  springmvc核心实现是DispatcherServlet
  在web.xml里面指定servlet由spring的DispatcherServlet作为实现。初始化参数contextConfigLocation为springmvc.xml的路径。
  在springmvc里面我们一般配置好模版引擎。和编码格式等。

3.spring aop
  spring aop面向切面编程(Aspect-Oriented-Programming)。主要解决松耦合的问题。
  首先spring有两种代理创建方式，一种是jdk创建的动态代理，一般是指实现了接口的类
  如果目标对象没有实现接口，那么spring则使用CGLIB库生成目标对象的子类。
  aop的四大概念:切面(Aspect)，连接点(Joinpoint), 通知(Advice), 切入点(Pointcut)

3.1 Pointcut
  首先要定义切面处理类，打个比方说我们要对某些操作做日志输出，那么可以做这么个切面处理类LogInterceptor,里面包含一个方法addLog(包含一些日志输出)
  然后在spring.xml里面定义<aop:config>节点
  接下来在<aop:config>里面定义切入条件<aop:pointcut expression="execution(* com.john.dao..*.*(..))" id="myPointcut"/>
  最后定义横切面<aop:aspect ref="myPointcut">,指定切入的条件
  那么就可以设置切入前置逻辑<aop:before method="addLog" pointcut-ref="myPointcut"/>

3.2 Joinpoint
  是一大堆切入点的集合也就是一堆pointcut集合

3.3 Advice
  其实也就是一个概念，就是说建议在某个方法前或者后，去做什么事情。实现也是上面那个实现 
  如果有Advice是Around的话，那么横切处理逻辑方法上要加一个参数叫做ProceedingJoinPoint，用来调用原方法逻辑。
  proceedingJoinPoint.proceed();在这个方法前后可以加额外的逻辑。

