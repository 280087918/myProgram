1.将本地文件复制到远程
  scp /usr/local/soft/jdk-7u79-linux-x64.tar.gz root@192.168.1.84:/usr/local/soft/

2.将远程文件复制到本地
  scp root@192.168.1.78:/usr/local/soft/apache-tomcat-7.0.69.zip /usr/local/soft

3.查看端口有没有开启
  netstat -tunlp|grep 9991

4.修改服务器时间
  date  -s "2015-5-8 19:48:00"

5.修改服务器名称
  修改完之后命令行开头会变成 [root@vm1 ~]# 
  vim /etc/sysconfig/network
  设置HOSTNAME=vm1，重启后生效
  hostname vm1。即时生效

6起停防火墙，即时生效，重启后失效:
  service iptables start
  service iptables stop
  起停防火墙，重启后生效:
  chkconfig iptables on
  chkconfig iptables off
