nginx��ʲô��


1.��װǰȷ��ϵͳ��װ���������
  yum install g++ gcc openssl-devel pcre-devel zlib-devel

2.������������tar�ļ�
  �����õ�������汾:nginx-1.9.15.tar.gz
  �ŵ�/usr/local���沢��ѹ��
  tar zxvf nginx-1.9.15.tar.gz

3.����nginxĿ¼ִ������������
  cd nginx-1.9.15

  ��鰲װ�ļ���������:
  ./configure --prefix=/usr/local/nginx-1.9.15
  make
  make install

4.��װ����
  �ղŰ�·��ֱ��ָ��Ϊ:user/local�ˣ�ֱ�ӵ���Ŀ¼�¸ɵ�nginx��������Ķ���������ִ������Ĳ����OK��

5.�ֶ�����logsĿ¼(Ҫ��Ȼ�����ᱨ�쳣)
  ��nginx��Ŀ¼ִ��mkdir logs

6.nginx��ͣ
  ����:sbin/nginx  --����Ҫ��nginx��Ŀ¼��ʹ��
  ֹͣ:ֱ��kill
	kill -9 nginx
  ����:sbin/nginx -s reload

7.��ΪĬ����80�˿ڣ�ǰֱ̨���������ip�����ɷ���nginx�ɹ�ҳ��
  ����nginx�����ɹ�


8.nginxʹ������
  ��鿴ͬ��Ŀ¼������˵��