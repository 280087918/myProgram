#查看都有什么binlog日志
show binary logs;

#查看某个binlog的日志
show binlog events in 'mysql-126-binlog.000015';

#将具体的binlog导出来分析,start-position:一开始的位置,stop-position:删除从啊做之前的位置
mysqlbinlog --start-position=892 --stop-position=2070 mysql-126-binlog.000015 > /usr/temp/binlog3.txt

#登录mysql客户端
source /usr/temp/binlog3.txt