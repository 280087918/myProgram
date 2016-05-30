这里主要是对nginx的配置文件[conf/nginx.conf]各个配置项做一个说明

=========
主体
=========
#配置用户和用户组，没什么用，不配置
user www www

#工作进程数，建议设置为cpu的总核数
#附录:查看cpu总核数[cat /proc/cpuinfo| grep "processor"| wc -l]
worker_processes  2;

#nginx输出日志的级别
error_log logs/error.log [debug | info | notice | warn | error | crit]

#记录主进程id文件,与ps -ef|grep nginx看到的主进程号是保持一致的
pid        logs/nginx.pid;

#一个进程允许打开的文件描述符最大值
#最好与linux文件描述符数保持一致,linux查看文件描述符命令[sysctl -a | grep fs.file]
worker_rlimit_nofile 65535;


=========
events
=========
#工作模式,linux2.6以上的用epoll
use epoll;

#单个进程允许的最大连接数
worker_connections  65535;

=========
http
=========
#文件扩展名与文件类型映射表
include       mime.types;

#默认文件类型
default_type  application/octet-stream;

#客户端请求体大小限制
client_body_buffer_size    8m;

#忽略不合法的请求头
ignore_invalid_headers   on;

#打开文件传输，一般应用都设置为on，如果没必要，关闭他可以节省io资源
sendfile                 on;

#长连接超时时间，单位是秒
keepalive_timeout  65

#使用gzip可以降低网络带宽，同时提升访问速度
gzip  on;             #开启gzip

gzip_min_length  1k;          #最小压缩大小

gzip_buffers     4 16k;        #压缩缓冲区

gzip_http_version 1.0;       #压缩版本

gzip_comp_level 2;            #压缩等级

gzip_types       text/plain application/x-JavaScript text/css application/xml;           #压缩类型

=========
upstream
=========
他是一个负载均衡的设置，使用轮询的机制
max_fails:允许失败的次数
weight=1轮询的权重

=========
server
=========
#本虚拟服务监听的端口号
listen 80

#域名
server_name mytest

#反向代理配置，将所有请求为http://mytest的请求全部转发到upstream中定义的目标服务器中。
location / {
    #此处配置的域名必须与upstream的域名一致，才能转发。
    proxy_pass     http://mytest;
    proxy_set_header   X-Real-IP $remote_addr;
}
