��װnode֮ǰҪ�Ȱ�װotter

���������ַ�ʽ��Ҫ��Ȼֱ�Ӵ�git������tar����Ҫ��Ȼ���Լ�����Դ�����
���˲��ܱ��룬�������õ�node�汾��maven����û��

��װ���òο�����
https://github.com/alibaba/otter/wiki/Manager_Quickstart
https://github.com/alibaba/otter/wiki/Node_Quickstart

https://github.com/alibaba/otter/wiki/Manager%E9%85%8D%E7%BD%AE%E4%BB%8B%E7%BB%8D
https://github.com/alibaba/otter/wiki/Manager%E4%BD%BF%E7%94%A8%E4%BB%8B%E7%BB%8D

1.github���ҵ�otter.git(������һ��)
  ��ǰ��https://github.com/alibaba/otter.git

2.cd��otterĿ¼���б���(������һ��)
  mvn clean install -Dmaven.test.skip -Denv=release

3.��git������manager.deployer-4.2.1.tar.gz
  �ϴ�������������ѹ��,ǰ���ȴ������ļ���
  tar zxvf manager.deployer-4.2.1.tar.gz  -C /usr/local/otter

4.�޸�conf/otter.properties
  ��Ҫ�޸����ݿ����ã�����ط���otter�Լ������ݿ⣬����ͬ�������ݿ�
  ����ע��otter�Ķ˿ںţ�����û�и������Ķ˿ں��ظ�
  �����޸�zookeeper�����ӵ�ַ����������ֱ��ʹ�ñ��ص�zookeeper���Ը�Ĭ������һ������
  !!ע���޸�otter.domainName��ʹ֮���ⲿ���ʵ�ipһ�£����������������ip��192.168.1.126,��ô���޸�Ϊ�Ǹ��ͺ�

5.��git�ϻ�ȡotter-manager-schema.sql������otter�趨�����ݿ������ʼ��
  ���������󣬲���Ҫ����룬��navicatֱ��F6ִ�м���

6.��ͣotter
  sh startup.sh
  sh stop.sh

7.���ʿ���̨
  http://192.168.1.126:8088 -��Ϊ���趨����8088�˿�
  Ĭ����������¼�ģ���������������κ����ã����Ҫ���ã���ô�ȵǳ����ٵ�¼
	admin/admin