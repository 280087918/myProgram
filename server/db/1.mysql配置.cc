1.有时候mysql连接数不够会出现下面的提示
  Can not connect to MySQL server. Too many connections
  查看连接数,mysql命令:show variables like 'max_connections';
  修改my.cnf
  添加:max_connections=2000
  sevice mysql restart
  OK!