================================================
本文档是基于windows64位系统安装的
================================================

~如果之前有mysql的话要先卸载干净
1.通过控制面板卸载mysql server

2.移除program file下面的mysql ini配置文件
  我是默认装的，所以在C盘的program file下
  然后我直接干掉整个Mysql文件夹的

3.清除Mysql在注册表中的信息
  regedit
  ->HKEY_LOCAL_MACHINE\SYSTEM\ControlSet001\services\eventlog\Application\MySQL
  -》HKEY_LOCAL_MACHINE\SYSTEM\ControlSet002\services\eventlog\Application\MySQL      
  删除这两个文件夹


~安装开始
1.从Mysql官网下载对应版本的64位操作系统的MySQL Server ZIP
  我下载的是:mysql-5.6.30-winx64.zip
  下载太慢了，用这个:mysql_5.6.24_winx64.zip

2.解压这个包
  我将他放在这路径下 D:\soft\mysql-5.6.24-winx64
  mysql-5.6.24-winx64是解压出来的根目录

3.为了能在命令行上使用mysql命令，所以要配置环境变量
  在PATH后面加入 ;D:\soft\mysql-5.6.24-winx64\bin

4.修改ini配置文件--这个配置不要做，反正是测试的，不改也可以，改了就出问题
  mysql-5.6.24-winx64目录下已经有my-default.ini
  复制一个出来，重命名为my.ini
  编辑这个ini
  修改里面的两个参数，这里面不要重新另外指定数据存放目录了，就用mysql根目录下的
  basedir(mysql安装目录，也就是我们解压后的目录)
  datadir(mysql数据存放的目录)
  basedir = D:\soft\mysql-5.6.24-winx64
  datadir = D:\soft\mysql-5.6.24-winx64\data

5.开始安装mysql服务
  命令行cd 到bin目录
  在这里也就是cd D:\soft\mysql-5.6.24-winx64\bin
  输入mysqld -install
  提示成功了，别高兴太早。

6.启动mysql服务
  net start mysql
  报错了 发生系统错误 1067
  传说是配置文件没弄好

7.像这种发生错误要先移除服务
  mysqld -remove
  这里还是成功的

8.起停mysql
  net start mysql
  net stop mysql
