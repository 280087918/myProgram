������Ҫ�Ƕ�nginx�������ļ�[conf/nginx.conf]������������һ��˵��

=========
����
=========
#�����û����û��飬ûʲô�ã�������
user www www

#��������������������Ϊcpu���ܺ���
#��¼:�鿴cpu�ܺ���[cat /proc/cpuinfo| grep "processor"| wc -l]
worker_processes  2;

#nginx�����־�ļ���
error_log logs/error.log [debug | info | notice | warn | error | crit]

#��¼������id�ļ�,��ps -ef|grep nginx�����������̺��Ǳ���һ�µ�
pid        logs/nginx.pid;

#һ����������򿪵��ļ����������ֵ
#�����linux�ļ�������������һ��,linux�鿴�ļ�����������[sysctl -a | grep fs.file]
worker_rlimit_nofile 65535;


=========
events
=========
#����ģʽ,linux2.6���ϵ���epoll
use epoll;

#����������������������
worker_connections  65535;

=========
http
=========
#�ļ���չ�����ļ�����ӳ���
include       mime.types;

#Ĭ���ļ�����
default_type  application/octet-stream;

#�ͻ����������С����
client_body_buffer_size    8m;

#���Բ��Ϸ�������ͷ
ignore_invalid_headers   on;

#���ļ����䣬һ��Ӧ�ö�����Ϊon�����û��Ҫ���ر������Խ�ʡio��Դ
sendfile                 on;

#�����ӳ�ʱʱ�䣬��λ����
keepalive_timeout  65

#ʹ��gzip���Խ����������ͬʱ���������ٶ�
gzip  on;             #����gzip

gzip_min_length  1k;          #��Сѹ����С

gzip_buffers     4 16k;        #ѹ��������

gzip_http_version 1.0;       #ѹ���汾

gzip_comp_level 2;            #ѹ���ȼ�

gzip_types       text/plain application/x-JavaScript text/css application/xml;           #ѹ������

=========
upstream
=========
����һ�����ؾ�������ã�ʹ����ѯ�Ļ���
max_fails:����ʧ�ܵĴ���
weight=1��ѯ��Ȩ��

=========
server
=========
#�������������Ķ˿ں�
listen 80

#����
server_name mytest

#����������ã�����������Ϊhttp://mytest������ȫ��ת����upstream�ж����Ŀ��������С�
location / {
    #�˴����õ�����������upstream������һ�£�����ת����
    proxy_pass     http://mytest;
    proxy_set_header   X-Real-IP $remote_addr;
}
