==================================
������
==================================
˵��:���������ֻ�Ǽ򵥵�һ�����ã�û�漰�����������Ϣ����ʵ����nginx�������һ��tomcat��Դ

�޸������ļ�:<nginx-root>/conf/nginx.conf

����Ľṹ����:

events{
}

http{
   server{
   }

   server{
   }
}

��ʼ�޸������ļ�
1.��serverƽ�����upstream
mytestָ��������������û�б�Ҫ��������������������ĸ�ʽ���������ֱ������ͨ�ַ���
upstream�ڵ������ö��server�Ļ��������⼸��server����������
upstream mytest {
   server 127.0.0.1:8078 max_fails=0 weight=1;
}

2. ��һ��server�ڵ㣬�޸������������Ϣ
�����ȡ��һ��Ҫ������(proxy_pass)
8077��nginx�������server���õ�һ�������˿ں�
server {
    listen       8077;
#    listen       somename:8080;
    server_name mytest;

    location / {
        proxy_pass     http://mytest/front-static/;
        root   html;
        index  index.html index.htm;
    }
}

3.��֤
  ����nginx
  �������������http://<nginx��������ַ>:8077����