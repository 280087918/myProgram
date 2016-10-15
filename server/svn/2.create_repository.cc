--创建仓库--

背景:需要先安装好svn
建立一个仓库ffzx
建立一个es的项目为例子

1.创建仓库
  mkdir -p /opt/svn/repository/ffzx
  svnadmin create /opt/svn/repository/ffzx

2.编辑版本库配置文件
  vim /opt/svn/repository/ffzx/conf/svnserve.conf
  #这里必须设置为none，要不然所有的用户不需要密码就可以访问仓库文件
  anon-access = none
  auth-access = write
  password-db = haocheng
  authz-db = authz
  realm = ffzx
  use-sasl = true

3.编辑用户名密码
  vim /opt/svn/repository/ffzx/conf/passwd
  #添加用户zhc，密码zhcpwd
  zhc = zhcpwd

4.设置权限
  vim /opt/svn/repository/ffzx/conf/authz

  #创建一个用户组叫devTeam，里面有一个用户是zhc，如果有多个那么用逗号隔开
  [groups]
  devTeam = zhc

  #用户zhc对根目录具有读写的权限
  [/]
  zhc = rw

  #用户组devTeam对项目es拥有读写的权限
  [ffzx:/es]
  @devTeam = rw
  
5.建立svn用户
  useradd svn
  passwd svn
  我这里设置的svn密码为svnpwd

6.设置svn用户可以操作svn目录(其实也没有必要，我这里直接用root启动svn)
  chown -R svn:svn /opt/svn/repository

7.启动svn
  svnserve -d -r /opt/svn/repository

  看svn是否启动成功
  netstat -tunlp | grep svn

8.配置开机自启动
  vim /etc/rc.d/rc.local
  /opt/svn/subversion/bin/svnserve -d –listen-port 3690 -r /opt/svn/repository

9.svn加入服务，这样就可以用svn的起停命令
  vim /etc/rc.d/init.d/svn
  输入文本 link-->同级目录的 3.create_svn_service.cc
  给文本赋权限
  chmod 755 /etc/rc.d/init.d/svn

  svn起停
  service svn stop
  service svn start

  杀svn进程
  killall svnserve

10.上面这种方式只能用svn://192.168.22.182/ffzx 这种方式访问
 
配置可能还有点问题，不能用，后面有时间再调试