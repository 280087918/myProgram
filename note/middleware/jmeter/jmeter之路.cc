���߱���:
	����ģ���߳�ȥ����صĽӿڣ�http�������ѹ�����ԣ�����������ر��档

�ο��ĵ�:
http://www.cnblogs.com/fnng/archive/2012/12/29/2839420.html

1.�ȴӹ���������������Ϊ3.2˵������������jdk8֮�ϣ���Ҳû�бȶ�֮ǰ�İ汾����ʲô��ϵ������������˸�2.8�汾��.
  apache-jmeter-2.8.zip���򵥵Ĳ�������ֱ������binĿ¼�µ�jmeter.bat����ѹ�����Ե����ú�ִ��

2.jmeter����ʹ�õĳ���
  a)http��https����
  b)SOAP
  c)���ݿ�(ֻ��ͨ��JDBC)
  d)LDAP
  e)JMS
  f)�����������shell�ű�

3.һ���򵥵�web����
  ����ֱ�Ӳ�����git�ϵ�spring-mvc��Ŀ
  a)��jmeter.bat
  b)���Լƻ�(�Ҽ�)->���->Threads(Users)->����߳���
	����:�߳���1
	�߳���:20 (�����߳���)
	Ramp-Up Period(in seconds):0
	ѭ������:5
  c)ִ�мƻ�->�߳���1(�Ҽ�)->���->����Ԫ��->HTTP Cookie������
	��Щ��վ��Ҫ��¼cookie������ò��ֱ���½����cookie����������
  d)ִ�мƻ�->�߳���1(�Ҽ�)->���->Sampler->HTTP����
	����������:localhost
	�˿�:80
	·��:index.sc
  e)ִ�мƻ�->�߳���1(�Ҽ�)->���->������->�鿴�����
  f)ֱ�ӵ�����

4.java����ѹ������
  ���ÿ��ǵ�����ִ��jar��������
  a)pom�ļ���������JMeter�Ŀ�����
  <dependency>  
      <groupId>org.apache.jmeter</groupId>
      <artifactId>ApacheJMeter_java</artifactId>
      <version>2.8</version>
  </dependency>
  b)�½�һ����̳�AbstractJavaSamplerClient
     ��runTest����дʵ�ִ���
     �����и�������Ҫע��:��������������棬�൱�����̵߳�Ԫ�������ڱ�д�Ĵ��������Ϊ���߳�˽�еġ�
     �����Եľ����������ʼ��spring context����ô����һ���̳߳�ʼ��һ��context��������Щcontext���໥������
  c)ֱ��mvc clean package�������
  d)��������jar���ŵ�jmeter��lib/extĿ¼�£����jar������Ҫ���õĵ�����jar������ô��Щ������jar����Ҫ�ŵ�libĿ¼��
  e)JMeter�½����Լƻ�(test-plan)
  f)�½��߳���(req-threads)
  g)�½�java����,ѡ�м̳���AbstractJavaSamplerClient�������
  h)�½��鿴�����

  �����Ҫ���ܲ�����������Ҫ����һ�������,��ô����"Java����"ҳǩ����Ӳ�����������Ϊ:count,ֵΪ${__Random(1,10)}������1-10֮��������
  �������棬runTest����д����������ô��ȡ�Ϳ�����.context.getIntParameter("count")
  JMeter���ӻ������о���һ��"��������"���԰�æ���ɺ�����

5.dubboѹ������
  ���ʵ�����ʵģ��Ҿ���һ���߳�new һ��ClassPathXmlApplicationContext����Ҳ��û������ġ���Ϊ�������ղ���Ҫѹ���jar����ֻ��Ҫѹ��ʵ�ֶˡ�
  ��������߳����Ƚ϶������£�����Ҫ�Ӵ�JMeter��jvm�ڴ档������jar������дһ��������ȡApplicationContext����

6.��UIѹ��
  linux�ϰ�װjmeter��ֱ���ϴ��������ص�zip��Ҳ�У���Ϊ�������sh�ű���������������
  vim /etc/profile
	���export PATH=/usr/local/apache-jmeter-2.8/bin:$PATH
  ��������İ�������˳��ѹ�⣬��ô�Ϳ��Խ����Լƻ����Ϊһ��jmx��xml�ṹ�ļ����Ժ�����ǻ����Ͻ����޸���չ���������Լƻ�����
	ע�⣬�������Ϊ��ʱ��һ��Ҫѡ����Լƻ�������������Ϊ��Ҫ��Ȼ�����
  jmeter -n -t dubbo-test-plan.jmx -l dubbo-test-plan.jtl
  ���ʽ���:n:nonguiģʽ�� t:��Ҫִ�еĽű����� , l:�������־

  �鿴jtl�ļ�
  more dubbo-test-plan.jtl
  <sample t="103" lt="0" ts="1495437617918" s="true" lb="Java Request" rc="" rm="" tn="theads-group 1-1" dt="text" by="14"/>
  t:�����ʱ����s:��ʾ���ؽ���ǳɹ����,tn:�߳�����,by:�������Ӧ�ֽ���

7.jmeter3.2(��Ҫjdk1.8����)
  jmeter -n -t my-webpost-plan.jmx -l my-webpost-plan.jtl -e -o webpost_report
  -e -o���°汾���еģ���Ϊjmx�ļ�����ָ�������ɾۺϱ��棬���������˼�ǽ��ۺϱ��������ָ��Ŀ¼webpost_report
  jmeter2.8�ǲ��߱���������
  ������ļ��п�������������������򿪽��й۲�