1.��admin��ݵ�¼otter����̨
admin/admin

2.Ҫ���node֮ǰ�����������zookeeper��Ⱥ
  ��Ҫ����дzookeeper��Ⱥ������

3.���node
  �����һ��node��otter������
  ip��д������ip������д127.0.0.1���������
  ��������:local
  �����˿���д:2088
  ���ض˿���д:9090
  ��������֮�󿴼��ڵ���δ����״̬
  �۲�ڵ���ţ�����ס,����ڵ������1

4.����node.deployer-4.2.1.tar.gz������ѹ��
   tar -zxvf node.deployer-4.2.12.tar.gz  -C /usr/local/otter_node1


5.��conf��Ŀ¼������nid
  ��Ϊ���Ǹ���otter����̨���õ�node�����1����������Ҫִ��������䣬��conf������nid
  echo 1 > conf/nid

6.�޸�conf�µ�otter.properties�ļ�
  ��Ҫ��ָ��otter.manager�ĵ�ַ,������Ҳ��д����ottermanager�ľ�����ip

7.��ͣ��binĿ¼
  sh startup.sh
  sh stop.sh

8.��������⣬��ô�Ͳ鿴��Ŀ¼�µ�logs/node/node.log

9.�������
  a.Ŀǰ���������⣬��ʾjava.net����UnknowHost ffzxtest : ffzxtest
  ��֪��ʲô�������ҵĻ����˻�˵����ʶ
  ����취��vi /etc/hosts ����127.0.0.1 ������� ffzxtest

  b.�Ҳ���zookeeper��Ⱥ
  ԭ��:��otter����̨������zookeeper��Ⱥ��ʱ��д��˿��ˡ�
  ����취:ɾ��node,ɾ��zookeeper��Ⱥ������Ū�����node��Ҫ�����ӣ�����Ҫ���ղ���(5)������node������nid

  c.�����װ�Ĺ�����������Ҫ��װaria2c(�������ð�װ��֮ǰ���ֵ������ǻ����ڴ治��)
	c-1)�������������µ�aria2��
		https://aria2.github.io/
		Ŀǰ���°汾��aria2-1.24.0.tar.gz,���µľ������
	c-2)��ѹ���ļ�
	        tar -xzvf aria2-1.24.0.tar.gz -C /usr/local
	c-3)�����ļ�
		./configure
		���ֱ��쳣��˵��gcc�汾����
	c-4)�������������µ�gcc��
		https://gcc.gnu.org/
		ע�⣬��Ҫ��News�����Release����
		Ŀǰ���°汾��xxx,������ô�100��M
		�Ҹ�����ľ������أ����ձ��ľ���
		wget http://ftp.tsukuba.wide.ad.jp/software/gcc/releases/gcc-6.1.0/gcc-6.1.0.tar.gz
	c-5)��װgccǰ������(GMP,MPFR,MPC)
		c-5-1)GMP��װ(4.2+)
			https://gmplib.org/#DOWNLOAD
			�����ص���gmp-6.1.0.tar.bz2
			Ϊ�˹����㴴��һ���ļ��� mkdir /usr/local/gcc,����ѹ��������
			tar -xjvf gmp-6.1.0.tar.bz2 -C /usr/local/gcc
			����Ŀ¼������ִ��
			./configure
			make
			make check
			make install
		c-5-2)MPFR��װ(2.4+)
			http://www.mpfr.org/mpfr-current/#download
			�����ص���mpfr-3.1.4.tar.gz
			��ѹ�� tar -xzvf mpfr-3.1.4.tar.gz -C /usr/local/gcc
			����Ŀ¼��������ִ��
			./configure --with-gmp-include=/usr/local/include --with-gmp-lib=/usr/local/lib
			make
			make check
			make install
		c-5-3)MPC(0.8.0+)��װ
			http://www.multiprecision.org
			�����ص��� mpc-1.0.2.tar.gz
			��װ���̸�GMP�İ�װ����һ��
		c-5-4)����Ҫisl 15+��
			http://isl.gforge.inria.fr/
			isl-0.16.1.tar.gz
			��װ������������c-5-1)һ��

	c-6)��װ��gcc
		��ѹ���ļ���/usr/local��
		�ڸ�Ŀ¼���½�gcc-1.6�ļ���
		cd��/gcc-1.6�ļ�����

	c-7)gcc��װ����
		��ԭĿ¼ִ��
		cd /usr/local/soft/gcc-6.1.0
		/usr/local/soft/gcc-6.1.0/configure --prefix=/gcc-1.6 --enable-threads=posix --disable-checking --enable--long-long --enable-languages=c,c++,java --enable-multilib
		......��ûװ��......
		make����
		װһ��glibc
		http://ftp.gnu.org/gnu/glibc/
		glibc-2.23/configure --prefix=/usr/local/gcc/glibc-2.23/