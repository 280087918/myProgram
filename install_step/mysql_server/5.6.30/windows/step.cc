================================================
���ĵ��ǻ���windows64λϵͳ��װ��
================================================

~���֮ǰ��mysql�Ļ�Ҫ��ж�ظɾ�
1.ͨ���������ж��mysql server

2.�Ƴ�program file�����mysql ini�����ļ�
  ����Ĭ��װ�ģ�������C�̵�program file��
  Ȼ����ֱ�Ӹɵ�����Mysql�ļ��е�

3.���Mysql��ע����е���Ϣ
  regedit
  ->HKEY_LOCAL_MACHINE\SYSTEM\ControlSet001\services\eventlog\Application\MySQL
  -��HKEY_LOCAL_MACHINE\SYSTEM\ControlSet002\services\eventlog\Application\MySQL      
  ɾ���������ļ���


~��װ��ʼ
1.��Mysql�������ض�Ӧ�汾��64λ����ϵͳ��MySQL Server ZIP
  �����ص���:mysql-5.6.30-winx64.zip
  ����̫���ˣ������:mysql_5.6.24_winx64.zip

2.��ѹ�����
  �ҽ���������·���� D:\soft\mysql-5.6.24-winx64
  mysql-5.6.24-winx64�ǽ�ѹ�����ĸ�Ŀ¼

3.Ϊ��������������ʹ��mysql�������Ҫ���û�������
  ��PATH������� ;D:\soft\mysql-5.6.24-winx64\bin

4.�޸�ini�����ļ�--������ò�Ҫ���������ǲ��Եģ�����Ҳ���ԣ����˾ͳ�����
  mysql-5.6.24-winx64Ŀ¼���Ѿ���my-default.ini
  ����һ��������������Ϊmy.ini
  �༭���ini
  �޸���������������������治Ҫ��������ָ�����ݴ��Ŀ¼�ˣ�����mysql��Ŀ¼�µ�
  basedir(mysql��װĿ¼��Ҳ�������ǽ�ѹ���Ŀ¼)
  datadir(mysql���ݴ�ŵ�Ŀ¼)
  basedir = D:\soft\mysql-5.6.24-winx64
  datadir = D:\soft\mysql-5.6.24-winx64\data

5.��ʼ��װmysql����
  ������cd ��binĿ¼
  ������Ҳ����cd D:\soft\mysql-5.6.24-winx64\bin
  ����mysqld -install
  ��ʾ�ɹ��ˣ������̫�硣

6.����mysql����
  net start mysql
  ������ ����ϵͳ���� 1067
  ��˵�������ļ�ûŪ��

7.�����ַ�������Ҫ���Ƴ�����
  mysqld -remove
  ���ﻹ�ǳɹ���

8.��ͣmysql
  net start mysql
  net stop mysql
