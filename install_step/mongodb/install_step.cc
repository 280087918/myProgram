参考资料:
http://www.yiibai.com/mongodb/mongodb_quick_guide.html
1.上mongodb官网，下载mongodb安装包
  http://www.mongodb.org/downloads
  网站上提供明文url，我这边直接就wget了

2.将文件下载到/usr/local/soft下
  mongodb-linux-x86_64-3.4.2.tgz
  并解压
  tar -xzvf mongodb-linux-x86_64-3.4.2.tgz

3.将文件移到上一级目录
  mv mongodb-linux-x86_64-3.4.2 /usr/local
  cd /usr/local
  mv mongodb-linux-x86_64-3.4.2 mongodb-3.4.2

4.创建data目录和log目录
  cd /usr/local/mongodb-3.4.2
  mkdir -p /mongo/data/db /mongo/logs

5.前台执行
  ./bin/mongod -dbpath=/mongo/data

6.后台执行
  手动创建mongod.log
  cd /mongo/logs
  vim mongod.log
  ./mongod -dbpath=/mongo/data -logpath=/mongo/logs/mongod.log --fork

7.关闭服务
  ./mongod -dbpath=/mongo/data -logpath=/mongo/logs/mongod.log --shutdown

8.出现异常
  ERROR: child process failed, exited with error number 1
  网上说是之前的mongo进程没有正确关闭
  这个问题跟我目前遇到的不一样

  