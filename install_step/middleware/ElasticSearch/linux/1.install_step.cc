�ο�����:
�ٷ���վ:https://www.elastic.co
http://www.cnblogs.com/tianjixiaoying/p/4316011.html

ʵ�鱳��:
������:192.168.22.181
����ʹ�õ���elasticsearch-2.3.4.zip
    Ϊʲô�������°汾2.3.5����Ϊik�ִ���Ŀǰֻ֧�ֵ�2.3.4

1.��ѹ��elasticsearch-2.3.4.zip
  unzip elasticsearch-2.3.4.zip -d /usr/local

2.����jdk��������
  ln -s /usr/local/jdk1.7.0_79/bin/java /bin/java

3.es��Ϊ��ȫ���ⲻ����rootֱ�����У�����elasticsearch�˻�
  groupadd -g 883 elasticsearch
  useradd -r -s /bin/bash -g elasticsearch elasticsearch -d /usr/local/elasticsearch-2.3.4/
  passwd elasticsearch
  
  ������˻���¼һ��:su elasticsearch
  �˳�:exit

4.��elasticsearch��Ŀ¼�´��������ļ���
  mkdir data logs

5.root�û���¼��elasticsearch�˻���es��װĿ¼��Ȩ
  chown -R elasticsearch /usr/local/elasticsearch-2.3.4/

6.�޸�config/elasticsearch.yml
  �޸����漸��:
  path.data: /usr/local/elasticsearch-2.3.4/data
  path.logs: /usr/local/elasticsearch-2.3.4/logs
  network.host: 192.168.22.181
  http.port: 9200
  transport.tcp.port: 9300

7.��elasticsearch�û�����es
  su elasticsearch
  ֱ������:bin/elasticsearch
  --��ʱ���ܿ����������ʱ���õ���9300�Ķ˿�
    http���ʵ���9200�Ķ˿�

8.�����ֱ������:
  http://192.168.22.181:9200/
  Ҫ���ܷ��ʾ�˵����װ�ɹ�

9.��װhead���
  elasticsearch�û�ִ��:
  cd bin
  ./plugin install mobz/elasticsearch-head
  �����ֱ������
  http://192.168.22.181:9200/_plugin/head/
  ���ʳɹ���head�����װ�ɹ�

10.�رս��̣����ػ���������es
  bin/elasticsearch -d

------�������------
JDK�汾��������ʾ������������ô���
�༭bin/elasticsearch�ű������
JAVA_OPTS='-XX:-UseSuperWord'

------��Ⱥ�(�ܼ�)------
�޸�config/elasticsearch.yml
cluster.name: my-cluster
node.name: node-1
node.rack: r1

����������ֻҪ��ͬһ���������棬���Ҽ�Ⱥ����һ���Ļ����ͻ����һ����Ⱥ
(��֪��Ϊʲô����Ⱥû���齨�ɹ����ȷ�һ�š��������о�)
�鿴��Ⱥ��״̬
http://192.168.22.181:9200/_cluster/health?pretty

��װ��header֮��
  ������������Ⱥ��״̬��green��

��װ��ik�ִ���֮���أ�
  ��װ�ã�����es����green��

��д�����ݿ���ô��
  д����֮��ͱ�yellow��

˳������һ�£���������������дes���ǲ��ܹ��ɼ�Ⱥ
�������������������Ž����������
ò�ƽ���ˡ�elasticsearch.yml����Ҫ����������
discovery.zen.ping.unicast.hosts: ["192.168.22.181:9300", "192.168.22.181:9301"]
