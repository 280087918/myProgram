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
   tar -zxvf node.deployer-4.2.12.tar.gz  -C /usr/local/otter_node1


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

  c.如果安装的过程中遇到需要安装aria2c(内网不用安装，之前出现的问题是机器内存不足)
	c-1)到官网下载最新的aria2包
		https://aria2.github.io/
		目前最新版本是aria2-1.24.0.tar.gz,我下的就是这个
	c-2)解压缩文件
	        tar -xzvf aria2-1.24.0.tar.gz -C /usr/local
	c-3)编译文件
		./configure
		发现报异常，说是gcc版本过低
	c-4)到官网下载最新的gcc包
		https://gcc.gnu.org/
		注意，不要点News哪里，点Release那里
		目前最新版本是xxx,这个包好大，100多M
		找个最近的镜像下载，找日本的镜像
		wget http://ftp.tsukuba.wide.ad.jp/software/gcc/releases/gcc-6.1.0/gcc-6.1.0.tar.gz
	c-5)安装gcc前置条件(GMP,MPFR,MPC)
		c-5-1)GMP安装(4.2+)
			https://gmplib.org/#DOWNLOAD
			我下载的是gmp-6.1.0.tar.bz2
			为了管理方便创建一个文件夹 mkdir /usr/local/gcc,并解压缩到这里
			tar -xjvf gmp-6.1.0.tar.bz2 -C /usr/local/gcc
			进入目录并依次执行
			./configure
			make
			make check
			make install
		c-5-2)MPFR安装(2.4+)
			http://www.mpfr.org/mpfr-current/#download
			我下载的是mpfr-3.1.4.tar.gz
			解压缩 tar -xzvf mpfr-3.1.4.tar.gz -C /usr/local/gcc
			进入目录，并依次执行
			./configure --with-gmp-include=/usr/local/include --with-gmp-lib=/usr/local/lib
			make
			make check
			make install
		c-5-3)MPC(0.8.0+)安装
			http://www.multiprecision.org
			我下载的是 mpc-1.0.2.tar.gz
			安装过程跟GMP的安装过程一致
		c-5-4)还需要isl 15+的
			http://isl.gforge.inria.fr/
			isl-0.16.1.tar.gz
			安装方法跟步骤与c-5-1)一致

	c-6)安装新gcc
		解压缩文件到/usr/local下
		在根目录下新建gcc-1.6文件夹
		cd到/gcc-1.6文件夹下

	c-7)gcc安装命令
		在原目录执行
		cd /usr/local/soft/gcc-6.1.0
		/usr/local/soft/gcc-6.1.0/configure --prefix=/gcc-1.6 --enable-threads=posix --disable-checking --enable--long-long --enable-languages=c,c++,java --enable-multilib
		......还没装完......
		make报错
		装一个glibc
		http://ftp.gnu.org/gnu/glibc/
		glibc-2.23/configure --prefix=/usr/local/gcc/glibc-2.23/