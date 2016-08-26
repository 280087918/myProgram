1.将本地文件复制到远程
  scp /usr/local/soft/jdk-7u79-linux-x64.tar.gz root@192.168.1.84:/usr/local/soft/

2.将远程文件复制到本地
  scp root@192.168.1.78:/usr/local/soft/apache-tomcat-7.0.69.zip /usr/local/soft

3.查看端口有没有开启
  netstat -tunlp|grep 9991