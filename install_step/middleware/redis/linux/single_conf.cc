����redis�İ�װ

1.�ӹ�������redis-3.2.0.tar.gz
  ���ŵ�/usr/local/soft/����

2.��ѹ��redis-3.2.0.tar.gz
  tar -xzvf redis-3.2.0.tar.gz -C /usr/local

3.���밲װ
  ��redis-3.2.0 SRCĿ¼��ִ��
  make
  make install

4.Ϊ�˷����������redis.conf���Ƴ���,��Ϊ����������ʱ����Ҫ�õ�redis.conf
  ��redis-3.2.0��Ŀ¼�ºõ�redis.conf
  cp redis.conf /etc/redis_6379.conf

5.�ǵò鿴�����ļ���bind����
  vi /etc/redis_6379.conf
  ���bindд����127.0.0.1
  ��ô��ĳ�bind 0.0.0.0
  ������0.0.0.0,��д������

6.����
  ò����make�ķ�ʽ��װ�ģ���д��ϵͳ�ķ���������
  ����ֱ������Ŀ¼ִ��
	redis-server /etc/redis_6379.conf &
