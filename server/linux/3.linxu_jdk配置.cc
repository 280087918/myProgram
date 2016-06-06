1.从官网下载tar包，并上传到服务器上
  jdk-7u79-linux-x64.tar.gz

2.解压缩tar包
  tar -xzvf jdk-7u79-linux-x64.tar.gz -C /usr/local/

3.在linux环境变量文件中加入以下配置
  vi /etc/profile
  
  在文件最后加入
  JAVA_HOME=/usr/local/jdk1.7.0_79
  PATH=$JAVA_HOME/bin:$PATH
  CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
  export JAVA_HOME
  export PATH
  export CLASSPATH

......貌似要reboot机器，要不然不起作用