======================================
通过erlang组建搭建rabbitmq集群

本次实验在3台虚拟机上测试
主:192.168.1.78
从:192.168.1.101
从:192.168.1.84
======================================
1.首先在那三个服务器都安装上rabbitmq，并通过网页登录看是否能顺利开启并登陆
  http://192.168.1.78:15672
  http://192.168.1.101:15672
  http://192.168.1.84:15672

2.在78的/usr/local/rabbitmq_cluster上创建文件startup.sh，并输入以下内容保存
  rabbitmq stop_app
  rabbitmqctl reset 
  rabbitmqctl cluster
  rabbitmqctl start_app

3.