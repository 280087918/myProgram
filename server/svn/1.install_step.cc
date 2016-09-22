============================
参考资料:
    http://jingyan.baidu.com/article/3c343ff7039de20d37796306.html
官方下载地址:
    http://subversion.apache.org/download

我这次下的是：subversion-1.9.4.tar.gz
为了以防万一，我将这个工具包上传到我的百度网盘当中
	技术>工具包>服务器>svn
	下面所依赖的包都扔到这里面

本次搭建在本机虚拟机当中:
    192.168.22.182
============================

1.在/opt下创建svn目录，因为/usr/local按照我平时的习惯会装一大堆软件
  cd /opt
  mkdir svn

2.将源文件先解压缩到/usr/local下
  因为下面需要解压一堆的依赖，所以在/usr/local/下面也创建一个svn目录，将解压的东西全部放那儿
  cd /usr/local
  mkdir svn
  tar -xzvf subversion-1.9.4.tar.gz -C /usr/local

3.编译文件
  cd /usr/local/svn/subversion-1.9.4
  ./configure --prefix=/opt/svn/svn

4.提示没有安装apr
 下载apr，和apr-util
 到官网下载:http://apr.apache.org/download.cgi
 目前下载的版本是:
 apr-1.5.2.tar.gz
 apr-util-1.5.4.tar.gz
 
5.将两个包都解压到/usr/local/svn目录下
 tar -xzvf apr-1.5.2.tar.gz -C /usr/local/svn
 tar -xzvf apr-util-1.5.4.tar.gz -C /usr/local/svn

6.安装apr的两个包
 cd /usr/local/svn/apr-1.5.2
 ./configure --prefix=/opt/svn/apr
 make
 make install

 cd /usr/local/svn/apr-util-1.5.4
 ./configure --prefix=/opt/svn/apr-util --with-apr=/opt/svn/apr
 make
 make install

7.继续安装svn，不过安装的时候后面多添加apr的两个参数
 cd /usr/local/svn/subversion-1.9.4
 ./configure --prefix=/opt/svn/subversion --with-apr=/opt/svn/apr --with-apr-util=/opt/svn/apr-util

8.这个时候提示需要安装sqlite.
 到官网下载:http://www.sqlite.org/download.html
 本次下载的是:S
 sqlite-autoconf-3140200.tar.gz

9.将sqlite包解压缩
  tar -xzvf sqlite-autoconf-3140200.tar.gz -C /usr/local/svn

10.安装sqlite
  /usr/local/svn/sqlite-autoconf-3140200
  ./configure --prefix=/opt/svn/sqlite
  make
  make install

11.继续安装svn，这里又多加了一个参数(注意一下有没有提示没有安装zlib，我这边是没有)
 cd /usr/local/svn/subversion-1.9.4
 ./configure --prefix=/opt/svn/subversion --with-apr=/opt/svn/apr --with-apr-util=/opt/svn/apr-util --with-sqlite=/opt/svn/sqlite
 make
 make install

12.修改配置文件，在配置文件最后加入以下语句
   #svn enviroment
   PATH=/opt/svn/subversion/bin:$PATH
   export path

   让配置即时生效
   source /etc/profile

13.验证svn有没有安装上
   svnserve --version
   如果有相关的版本信息输出，那么就是安装成功