1.��admin��ݵ�¼otter����̨
admin/admin

2.Ҫ���node֮ǰ�����������zookeeper��Ⱥ
  ��Ҫ����дzookeeper��Ⱥ������

3.���node
  �����һ��node��otter������
  ip��д������ip������д127.0.0.1���������
  ��������:local
  �����˿���д:2088
  ���ض˿���д:9090
  ��������֮�󿴼��ڵ���δ����״̬
  �۲�ڵ���ţ�����ס,����ڵ������1

4.����node.deployer-4.2.1.tar.gz������ѹ��
   tar zxvf node.deployer-4.2.1.tar.gz  -C /usr/local/otter_node1


5.��conf��Ŀ¼������nid
  ��Ϊ���Ǹ���otter����̨���õ�node�����1����������Ҫִ��������䣬��conf������nid
  echo 1 > conf/nid

6.�޸�conf�µ�otter.properties�ļ�
  ��Ҫ��ָ��otter.manager�ĵ�ַ,������Ҳ��д����ottermanager�ľ�����ip

7.��ͣ��binĿ¼
  sh startup.sh
  sh stop.sh

8.��������⣬��ô�Ͳ鿴��Ŀ¼�µ�logs/node/node.log

9.�������
  a.Ŀǰ���������⣬��ʾjava.net����UnknowHost ffzxtest : ffzxtest
  ��֪��ʲô�������ҵĻ����˻�˵����ʶ
  ����취��vi /etc/hosts ����127.0.0.1 ������� ffzxtest

  b.�Ҳ���zookeeper��Ⱥ
  ԭ��:��otter����̨������zookeeper��Ⱥ��ʱ��д��˿��ˡ�
  ����취:ɾ��node,ɾ��zookeeper��Ⱥ������Ū�����node��Ҫ�����ӣ�����Ҫ���ղ���(5)������node������nid