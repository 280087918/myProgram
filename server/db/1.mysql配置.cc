1.��ʱ��mysql����������������������ʾ
  Can not connect to MySQL server. Too many connections
  �鿴������,mysql����:show variables like 'max_connections';
  �޸�my.cnf
  ���:max_connections=2000
  sevice mysql restart
  OK!