安装node之前要先安装otter

可以有两种方式，要不然直接从git上下载tar包，要不然就自己下载源码编译
试了不能编译，里面引用的node版本，maven上面没有

安装配置参考资料
https://github.com/alibaba/otter/wiki/Manager_Quickstart
https://github.com/alibaba/otter/wiki/Node_Quickstart

https://github.com/alibaba/otter/wiki/Manager%E9%85%8D%E7%BD%AE%E4%BB%8B%E7%BB%8D
https://github.com/alibaba/otter/wiki/Manager%E4%BD%BF%E7%94%A8%E4%BB%8B%E7%BB%8D

1.github上找到otter.git(跳过这一步)
  当前是https://github.com/alibaba/otter.git

2.cd到otter目录进行编译(跳过这一步)
  mvn clean install -Dmaven.test.skip -Denv=release

3.到git上下载manager.deployer-4.2.1.tar.gz
  上传到服务器并解压缩,前提先创建好文件夹
  tar zxvf manager.deployer-4.2.1.tar.gz  -C /usr/local/otter

4.修改conf/otter.properties
  主要修改数据库配置，这个地方是otter自己的数据库，不是同步的数据库
  还有注意otter的端口号，看有没有跟其他的端口号重复
  还有修改zookeeper的连接地址，我这里是直接使用本地的zookeeper所以跟默认配置一样即可
  !!注意修改otter.domainName，使之与外部访问的ip一致，如我这里机器内网ip是192.168.1.126,那么就修改为那个就好

5.从git上获取otter-manager-schema.sql，并在otter设定的数据库上面初始化
  数据量不大，不需要命令导入，用navicat直接F6执行即可

6.启停otter
  sh startup.sh
  sh stop.sh

7.访问控制台
  http://192.168.1.126:8088 -因为我设定的是8088端口
  默认是匿名登录的，这种情况不能做任何配置，如果要配置，那么先登出，再登录
	admin/admin