==================================
简单配置
==================================
说明:这里的配置只是简单的一个配置，没涉及具体的描述信息，本实验是nginx反向代理一个tomcat资源

修改配置文件:<nginx-root>/conf/nginx.conf

里面的结构如下:

events{
}

http{
   server{
   }

   server{
   }
}

开始修改配置文件
1.与server平级添加upstream
mytest指的是域名，但是没有必要是真的域名，或者域名的格式，我这里就直接是普通字符串
upstream节点中配置多个server的话就是在这几个server里面做负载
upstream mytest {
   server 127.0.0.1:8078 max_fails=0 weight=1;
}

2. 找一个server节点，修改里面的配置信息
代理的取向一定要配置上(proxy_pass)
8077是nginx对于这个server配置的一个监听端口号
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

3.验证
  重启nginx
  打开浏览器，输入http://<nginx服务器地址>:8077即可