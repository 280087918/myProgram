单个redis的安装

1.从官网下载redis-3.2.0.tar.gz
  并放到/usr/local/soft/下面

2.解压缩redis-3.2.0.tar.gz
  tar -xzvf redis-3.2.0.tar.gz -C /usr/local

3.编译安装
  在redis-3.2.0 SRC目录下执行
  make
  make install

4.为了方便起见，将redis.conf复制出来,因为后续启动的时候需要用到redis.conf
  在redis-3.2.0根目录下好到redis.conf
  cp redis.conf /etc/redis_6379.conf

5.记得查看配置文件的bind属性
  vi /etc/redis_6379.conf
  如果bind写的是127.0.0.1
  那么则改成bind 0.0.0.0
  必须是0.0.0.0,不写都不行

6.运行
  貌似用make的方式安装的，都写到系统的服务里面了
  所以直接任意目录执行
	redis-server /etc/redis_6379.conf &
