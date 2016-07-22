1.修改bin目录下的catalina.sh
  找到这一行# ----- Execute The Requested Co
  并在这行附近输入:
  CATALINA_OPTS="$CATALINA_OPTS -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9999 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=true -Dcom.sun.management.jmxremote.password.file=../conf/jmxremote.password -Dcom.sun.management.jmxremote.access.file=../conf/jmxremote.access -Djava.rmi.server.hostname=192.168.1.195"

2.如上图配置所述，需要在tomcat的conf目录下创建两个文件，分别是
  jmxremote.password, jmxremote.access
  touch jmxremote.password jmxremote.access
  jmxremote.access:
  monitorRole   readonly
  controlRole   readwrite

  jmxremote.password:
  monitorRole ffzx2016
  controlRole ffzx2016

3.给两个文件赋权限，不能给太大,就给600
  chmod 600 jmxremote.*

4.使用catalina.sh start启动

5.使用本地jvm检测工具检测
  使用jdk，bin目录下的jmc.exe