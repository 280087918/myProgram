1.以admin身份登录otter控制台
admin/admin

2.要添加node之前，必须先添加zookeeper集群
  按要求填写zookeeper集群并保存

3.添加node
  先添加一个node在otter机器下
  ip填写局域网ip，不填写127.0.0.1以免出问题
  机器名称:local
  机器端口填写:2088
  下载端口填写:9090
  保存完了之后看见节点是未启动状态
  观察节点序号，并记住,这里节点序号是1

4.下载node.deployer-4.2.1.tar.gz，并解压缩
   tar zxvf node.deployer-4.2.1.tar.gz  -C /usr/local/otter_node1


5.到conf根目录下生成nid
  因为我们刚在otter控制台配置的node序号是1，所以这里要执行下面语句，给conf下生成nid
  echo 1 > conf/nid

6.修改conf下的otter.properties文件
  主要是指定otter.manager的地址,这里我也填写的是ottermanager的局域网ip

7.启停，bin目录
  sh startup.sh
  sh stop.sh

8.如果有问题，那么就查看根目录下的logs/node/node.log

9.问题汇总
  a.目前遇到的问题，提示java.net……UnknowHost ffzxtest : ffzxtest
  不知道什么鬼，访问我的机器账户说不认识
  解决办法，vi /etc/hosts 并在127.0.0.1 后面添加 ffzxtest

  b.找不到zookeeper集群
  原因:在otter控制台上配置zookeeper集群的时候写错端口了。
  解决办法:删掉node,删掉zookeeper集群，重新弄，这个node需要会增加，所以要按照步骤(5)重新在node上生成nid