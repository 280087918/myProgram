============================
�ο�����:
    http://jingyan.baidu.com/article/3c343ff7039de20d37796306.html
�ٷ����ص�ַ:
    http://subversion.apache.org/download

������µ��ǣ�subversion-1.9.4.tar.gz
Ϊ���Է���һ���ҽ�������߰��ϴ����ҵİٶ����̵���
	����>���߰�>������>svn
	�����������İ����ӵ�������

���δ�ڱ������������:
    192.168.22.182
============================

1.��/opt�´���svnĿ¼����Ϊ/usr/local������ƽʱ��ϰ�߻�װһ������
  cd /opt
  mkdir svn

2.��Դ�ļ��Ƚ�ѹ����/usr/local��
  ��Ϊ������Ҫ��ѹһ�ѵ�������������/usr/local/����Ҳ����һ��svnĿ¼������ѹ�Ķ���ȫ�����Ƕ�
  cd /usr/local
  mkdir svn
  tar -xzvf subversion-1.9.4.tar.gz -C /usr/local

3.�����ļ�
  cd /usr/local/svn/subversion-1.9.4
  ./configure --prefix=/opt/svn/svn

4.��ʾû�а�װapr
 ����apr����apr-util
 ����������:http://apr.apache.org/download.cgi
 Ŀǰ���صİ汾��:
 apr-1.5.2.tar.gz
 apr-util-1.5.4.tar.gz
 
5.������������ѹ��/usr/local/svnĿ¼��
 tar -xzvf apr-1.5.2.tar.gz -C /usr/local/svn
 tar -xzvf apr-util-1.5.4.tar.gz -C /usr/local/svn

6.��װapr��������
 cd /usr/local/svn/apr-1.5.2
 ./configure --prefix=/opt/svn/apr
 make
 make install

 cd /usr/local/svn/apr-util-1.5.4
 ./configure --prefix=/opt/svn/apr-util --with-apr=/opt/svn/apr
 make
 make install

7.������װsvn��������װ��ʱ���������apr����������
 cd /usr/local/svn/subversion-1.9.4
 ./configure --prefix=/opt/svn/subversion --with-apr=/opt/svn/apr --with-apr-util=/opt/svn/apr-util

8.���ʱ����ʾ��Ҫ��װsqlite.
 ����������:http://www.sqlite.org/download.html
 �������ص���:S
 sqlite-autoconf-3140200.tar.gz

9.��sqlite����ѹ��
  tar -xzvf sqlite-autoconf-3140200.tar.gz -C /usr/local/svn

10.��װsqlite
  /usr/local/svn/sqlite-autoconf-3140200
  ./configure --prefix=/opt/svn/sqlite
  make
  make install

11.������װsvn�������ֶ����һ������(ע��һ����û����ʾû�а�װzlib���������û��)
 cd /usr/local/svn/subversion-1.9.4
 ./configure --prefix=/opt/svn/subversion --with-apr=/opt/svn/apr --with-apr-util=/opt/svn/apr-util --with-sqlite=/opt/svn/sqlite
 make
 make install

12.�޸������ļ����������ļ��������������
   #svn enviroment
   PATH=/opt/svn/subversion/bin:$PATH
   export path

   �����ü�ʱ��Ч
   source /etc/profile

13.��֤svn��û�а�װ��
   svnserve --version
   �������صİ汾��Ϣ�������ô���ǰ�װ�ɹ�