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
  ./configure --prefix=/usr/local/nginx-1.9.15 --conf-path=/usr/local/nginx-1.9.15/nginx.conf
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

9.���⣬���������nginx����ķ�����Դʱ����ERR_CONTENT_LENGTH_MISMATCH
      ngxin�Ὣһ�����ļ����浽%nginx%/proxy_tempĿ¼����
  a)ps -ef|grep ngxin
      ����ngxin����������root������������worker process����������nobody
      ��������Ҫ�����ǽ��������������Ҳ��������ngxin��ͬ�����û�
  b)��ֹͣ����,˳����
       ��ֹͣtomcat,��ֹͣnginx
  c)��ȥ��ִ������:ll proxy_temp �鿴�����ߣ����ǲ���nobody
  d)�¹��ǲ���nobody��ֱ�ӽ�nginx��Ȩ��ȫ����root
	chown -R root:root *
  e)�޸�conf/nginx.conf
        vi nginx.conf
	��worker_processes֮ǰ����
	user root;
	�����˳�
  f)��������tomcat������ngxin