=============
��װ
=============
1.����redis�İ�װ�ܼ򵥣�redis����û�����windows�汾�ġ�����windowsƽ̨�µ�redis������github�����ṩ
����zip��ѹ����ģ���Ҫmis��ġ�(��Ϊmis���ֱ����windows�°�װ�˷���������̫ǿ)

2.��ѹ����ֱ�������ļ��������redis-server.exe����


=============
����
=============
��ʵ����ͬһ̨�����µ�����redis����

1.����ѹ�����ļ��ֱ�Ž������ļ����ڣ�һ���ļ���Ϊmaster����һ��Ϊslave

2.�޸�salve�ļ����µ�redis.windows.conf
  ���˿ڴ�6379�޸�Ϊ6380
  port 6380

3.�޸�slaveof��������������˭�Ĵӽڵ㣬Ҳ����Ҫָ��master����Ϣ
  slaveof 127.0.0.1 6379

4.master�����κ��޸ģ����������������master��������slave
  ����ʱҪָ�������޸ĵ�conf�ļ���ַ,ͨ��cmd����
  ����master:redis-server.exe redis.windows.conf
  ����slave:ͬ��