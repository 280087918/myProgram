1.struts2
  web.xml��������Struts2�ĺ��Ŀ��������������ؿͻ������󣬲�������ת������Ӧ��Action��������
  struts2����web������filter�������صġ����ص�����struts2���µ�dispatcher.FilterDispatcher.
  ʹ�õĹ�������struts.xml�������ú����Լ�������ӳ���ϵ��ǰ�˷�����Դ��ʱ����Ǳ���+�������ķ�ʽ���з���.

2.springmvc
  springmvc����ʵ����DispatcherServlet
  ��web.xml����ָ��servlet��spring��DispatcherServlet��Ϊʵ�֡���ʼ������contextConfigLocationΪspringmvc.xml��·����
  ��springmvc��������һ�����ú�ģ�����档�ͱ����ʽ�ȡ�

3.spring aop
  spring aop����������(Aspect-Oriented-Programming)����Ҫ�������ϵ����⡣
  ����spring�����ִ�������ʽ��һ����jdk�����Ķ�̬����һ����ָʵ���˽ӿڵ���
  ���Ŀ�����û��ʵ�ֽӿڣ���ôspring��ʹ��CGLIB������Ŀ���������ࡣ
  aop���Ĵ����:����(Aspect)�����ӵ�(Joinpoint), ֪ͨ(Advice), �����(Pointcut)

3.1 Pointcut
  ����Ҫ�������洦���࣬����ȷ�˵����Ҫ��ĳЩ��������־�������ô��������ô�����洦����LogInterceptor,�������һ������addLog(����һЩ��־���)
  Ȼ����spring.xml���涨��<aop:config>�ڵ�
  ��������<aop:config>���涨����������<aop:pointcut expression="execution(* com.john.dao..*.*(..))" id="myPointcut"/>
  ����������<aop:aspect ref="myPointcut">,ָ�����������
  ��ô�Ϳ�����������ǰ���߼�<aop:before method="addLog" pointcut-ref="myPointcut"/>

3.2 Joinpoint
  ��һ��������ļ���Ҳ����һ��pointcut����

3.3 Advice
  ��ʵҲ����һ���������˵������ĳ������ǰ���ߺ�ȥ��ʲô���顣ʵ��Ҳ�������Ǹ�ʵ�� 
  �����Advice��Around�Ļ�����ô���д����߼�������Ҫ��һ����������ProceedingJoinPoint����������ԭ�����߼���
  proceedingJoinPoint.proceed();���������ǰ����ԼӶ�����߼���

