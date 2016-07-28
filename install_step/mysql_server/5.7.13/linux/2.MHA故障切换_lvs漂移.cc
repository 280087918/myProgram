参考资料:http://www.cnblogs.com/yuanermen/p/3735263.html

说明:
    本次实验是继承上一次实验的，而且在这之上还做了点变动
    故障迁移一般只有一个备机就够了，而且之前都是直接故障切换到81
	所以我们本次实验就只在【主98】和【备81】上面做lvs

1.主备安装lvs
    yum install -y keepalived

2.【主98】操作
   a)新增文件
   vim /etc/keepalived/mysql_down.sh
   并输入：
	#!/bin/bash
	pkill keepalived
	# chmod +x /root/mysql_down.sh #授权可执行权限
   b)修改/etc/keepalived/keepalived.conf文件
        并粘贴本路径下的 "2.MHA故障切换_lvs漂移_keepalived_98.conf" 的文件内容
   
3.【备81】操作
   a)如step2一样，新增一个/etc/keepalived/mysql_down.sh
   b)修改/etc/keepalived/keepalived.conf文件
        并粘贴本路径下的 "2.MHA故障切换_lvs漂移_keepalived_81.conf" 的文件内容

4.keepalived起停
   service keepalived start
   service keepalived stop
   service keepalived restart

5.原理:如果本机的3306接口不通，那么就杀掉本机的keepalived进程，让lvs自动到另外一台机器上访问3306资源