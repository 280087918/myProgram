nginx是什么？


1.安装前确保系统安装了以下组件
  yum install g++ gcc openssl-devel pcre-devel zlib-devel

2.到官网上下载tar文件
  本次用的是这个版本:nginx-1.9.15.tar.gz
  放到/usr/local下面并解压缩
  tar zxvf nginx-1.9.15.tar.gz

3.进入nginx目录执行相关命令并配置
  cd nginx-1.9.15

  检查安装文件的完整性:
  ./configure --prefix=/usr/local/nginx-1.9.15 --conf-path=/usr/local/nginx-1.9.15/nginx.conf
  make
  make install

4.安装错了
  刚才把路径直接指定为:user/local了，直接到改目录下干掉nginx编译出来的东西，重新执行上面的步骤就OK了

5.手动创建logs目录(要不然启动会报异常)
  到nginx跟目录执行mkdir logs

6.nginx启停
  启动:sbin/nginx  --必须要在nginx根目录下使用
  停止:直接kill
	kill -9 nginx
  重启:sbin/nginx -s reload

7.因为默认用80端口，前台直接输入机器ip，即可返回nginx成功页面
  这样nginx启动成功


8.nginx使用配置
  请查看同级目录的配置说明

9.问题，浏览器访问nginx代理的服务资源时出现ERR_CONTENT_LENGTH_MISMATCH
      ngxin会将一部分文件缓存到%nginx%/proxy_temp目录里面
  a)ps -ef|grep ngxin
      发现ngxin的主进程是root，而工作进程worker process的所有者是nobody
      现在我们要做的是将这个主工作进程也给跟启动ngxin的同样的用户
  b)先停止服务,顺序是
       先停止tomcat,再停止nginx
  c)进去后执行命令:ll proxy_temp 查看所有者，看是不是nobody
  d)甭管是不是nobody，直接将nginx的权限全部给root
	chown -R root:root *
  e)修改conf/nginx.conf
        vi nginx.conf
	在worker_processes之前输入
	user root;
	保存退出
  f)重新启动tomcat，还有ngxin