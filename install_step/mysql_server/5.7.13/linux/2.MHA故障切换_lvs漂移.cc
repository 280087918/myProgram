�ο�����:http://www.cnblogs.com/yuanermen/p/3735263.html

˵��:
    ����ʵ���Ǽ̳���һ��ʵ��ģ���������֮�ϻ����˵�䶯
    ����Ǩ��һ��ֻ��һ�������͹��ˣ�����֮ǰ����ֱ�ӹ����л���81
	�������Ǳ���ʵ���ֻ�ڡ���98���͡���81��������lvs

1.������װlvs
    yum install -y keepalived

2.����98������
   a)�����ļ�
   vim /etc/keepalived/mysql_down.sh
   �����룺
	#!/bin/bash
	pkill keepalived
	# chmod +x /root/mysql_down.sh #��Ȩ��ִ��Ȩ��
   b)�޸�/etc/keepalived/keepalived.conf�ļ�
        ��ճ����·���µ� "2.MHA�����л�_lvsƯ��_keepalived_98.conf" ���ļ�����
   
3.����81������
   a)��step2һ��������һ��/etc/keepalived/mysql_down.sh
   b)�޸�/etc/keepalived/keepalived.conf�ļ�
        ��ճ����·���µ� "2.MHA�����л�_lvsƯ��_keepalived_81.conf" ���ļ�����

4.keepalived��ͣ
   service keepalived start
   service keepalived stop
   service keepalived restart

5.ԭ��:���������3306�ӿڲ�ͨ����ô��ɱ��������keepalived���̣���lvs�Զ�������һ̨�����Ϸ���3306��Դ