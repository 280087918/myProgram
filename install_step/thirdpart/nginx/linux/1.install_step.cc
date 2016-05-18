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
  ./configure --prefix=/usr/local/nginx-1.9.15
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