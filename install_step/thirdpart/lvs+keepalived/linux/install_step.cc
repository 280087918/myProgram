=====================
背景介绍:
本次实验全部都在我的虚拟机下进行，一共4台机器
ip分别是:
192.168.1.78、192.168.1.83、192.168.1.84、192.168.1.85

具体是这么分配:
192.168.1.78 负载均衡-主机
192.168.1.83 负载均衡-备机
192.168.1.84 web服务1
192.168.1.85 web服务2
=====================

1.我的web容器都是tomcat，而且都是使用80端口
  在84和85上安装jdk和tomcat
  并且新增一个index.html,在里面说明这个response是从哪里来的
  比如写上this page is from 192.168.1.84

2.在两台web服务器84和85上设置虚拟ip(VIP)
  找一个内网没有在使用的ip,我这里用的是120
  2_a)进入目录/etc/init.d/ ，新建文件realserver
	vi realserver
  2_b)输入以下内容,请在同级目录寻找install_step_weblvs.cc,复制里面的代码
  2_c)保存后，给文件赋权限
        chmod 777 realserver
  2_d)为了防止运行的时候说没有functions的权限，所以先给functions权限
      cd /etc/rc.d/init.d/
      chmod 777 functions
  2_e)开启realserver服务
      service realserver start

3.负载主机安装keepalived
  yum install keepalived

4.修改负载主机的keepalived配置
  cd /etc/keepalived
  为了保险，将原来的conf文件备份
  cp keepalived.conf keepalived.conf.bak
  清空原来文件
  >keepalived.conf
  将install_step_master.cc内容粘贴到keepalived.conf文件里面去

5.启动负载主机keepalived服务
  service keepalived start

6.负载备机跟主机操作一样，先安装

7.负载备机配置跟负载主机大体一致，有个别地方需要修改
  修改state,state BACKUP
  因为主的priority是100，所以备机不能高于主机，只能最多99
  修改priority,priority 99

8.启动备机keepalived服务
  service keepalived start

9.访问
  http://192.168.1.120/index.html
  火狐,ctrl+f5 交替显示