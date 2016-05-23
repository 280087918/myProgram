vagrant虚拟机搭建

1.先下载VirtualBox-5.0.20-106931-Win.exe
  http://downloads.vagrantup.com/
  并安装

2.下载vagrant组件，以使用vagrant命令
  https://www.vagrantup.com/downloads.html
  安装完之后就可以用vagrant命令了，不过要重启机器

3.下载centos-6.6-x86_64.box
  并放置在本机目录下
	E:\virtual_linux\_vagrant_centos6.4

4.到达上面的目录并执行命令
  vagrant box add _centos6.6 centos-6.6-x86_64.box

5.查看box列表
  vagrant box list

6.初始化vagrant
  vagrant init _centos6.6

7.看到同级目录下出现Vagrantfile这个文件即是成功的

8.启动虚拟机
  vagrant up

9.虚拟机允许ssh登录
  vagrant ssh

10.ssh登录
   ip:127.0.0.1 端口2222
   vagrant/root 密码vagrant

11.起停虚拟机
   启动:vagrant up
   停止:vagrant halt
   重启:vagrant reload

12.安装完之后查看语言，有可能是其他语言的，我看了一下，就给我装的德语的
   查看当前linux系统语言:echo $LANG
   查看系统中有没有中文语言包:locale,如果有zh_CN那么就是有中文语言包
   如果没有，那么就通过yum下载:yum groupinstall chinese-support
   将系统语言修改为中文:
	Vi  /etc/sysconfig/i18n
	LANG="zh_CN.UTF-8"
   修改好之后重启系统

to be continue......
http://jingyan.baidu.com/album/c1a3101eb2b8dbde656deb32.html?picindex=3
赶脚不太好用