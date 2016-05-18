项目说明:
本项目意指方便快速搭建一个spring mvc的项目，里面可能会包含一些其他业务功能点，本项目不侧重UI

主要功能点:
1.spring mvc
2.统一异常处理机制
	使用spring组建对异常做统一处理
		编写JExceptionHandler实现HandlerExceptionResolver(spring接口)，里面包含日志的处理逻辑，及试图的返回
		applicationContext-base.xml里面加入配置让spring管理上面创建的bean
	另外一种方式，还是使用spring的组建，不过方式不一样，使用@ExceptionHandler注解，这种直接放到Controller里面
		比较靠谱的方法就是放到底层的Controller当中