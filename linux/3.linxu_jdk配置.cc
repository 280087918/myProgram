1.�ӹ�������tar�������ϴ�����������
  jdk-7u79-linux-x64.tar.gz

2.��ѹ��tar��
  tar -xzvf jdk-7u79-linux-x64.tar.gz -C /usr/local/

3.��linux���������ļ��м�����������
  vi /etc/profile
  
  ���ļ�������
  JAVA_HOME=/usr/local/jdk1.7.0_79
  PATH=$JAVA_HOME/bin:$PATH
  CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
  export JAVA_HOME
  export PATH
  export CLASSPATH

......ò��Ҫreboot������Ҫ��Ȼ��������