1.�޸�binĿ¼�µ�catalina.sh
  �ҵ���һ��# ----- Execute The Requested Co
  �������и�������:
  CATALINA_OPTS="$CATALINA_OPTS -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9999 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=true -Dcom.sun.management.jmxremote.password.file=../conf/jmxremote.password -Dcom.sun.management.jmxremote.access.file=../conf/jmxremote.access -Djava.rmi.server.hostname=192.168.1.195"

2.����ͼ������������Ҫ��tomcat��confĿ¼�´��������ļ����ֱ���
  jmxremote.password, jmxremote.access
  touch jmxremote.password jmxremote.access
  jmxremote.access:
  monitorRole   readonly
  controlRole   readwrite

  jmxremote.password:
  monitorRole ffzx2016
  controlRole ffzx2016

3.�������ļ���Ȩ�ޣ����ܸ�̫��,�͸�600
  chmod 600 jmxremote.*

4.ʹ��catalina.sh start����

5.ʹ�ñ���jvm��⹤�߼��
  ʹ��jdk��binĿ¼�µ�jmc.exe