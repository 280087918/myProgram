vagrant������

1.������VirtualBox-5.0.20-106931-Win.exe
  http://downloads.vagrantup.com/
  ����װ

2.����vagrant�������ʹ��vagrant����
  https://www.vagrantup.com/downloads.html
  ��װ��֮��Ϳ�����vagrant�����ˣ�����Ҫ��������

3.����centos-6.6-x86_64.box
  �������ڱ���Ŀ¼��
	E:\virtual_linux\_vagrant_centos6.4

4.���������Ŀ¼��ִ������
  vagrant box add _centos6.6 centos-6.6-x86_64.box

5.�鿴box�б�
  vagrant box list

6.��ʼ��vagrant
  vagrant init _centos6.6

7.����ͬ��Ŀ¼�³���Vagrantfile����ļ����ǳɹ���

8.���������
  vagrant up

9.���������ssh��¼
  vagrant ssh

10.ssh��¼
   ip:127.0.0.1 �˿�2222
   vagrant/root ����vagrant

11.��ͣ�����
   ����:vagrant up
   ֹͣ:vagrant halt
   ����:vagrant reload

12.��װ��֮��鿴���ԣ��п������������Եģ��ҿ���һ�£��͸���װ�ĵ����
   �鿴��ǰlinuxϵͳ����:echo $LANG
   �鿴ϵͳ����û���������԰�:locale,�����zh_CN��ô�������������԰�
   ���û�У���ô��ͨ��yum����:yum groupinstall chinese-support
   ��ϵͳ�����޸�Ϊ����:
	Vi  /etc/sysconfig/i18n
	LANG="zh_CN.UTF-8"
   �޸ĺ�֮������ϵͳ

to be continue......
http://jingyan.baidu.com/album/c1a3101eb2b8dbde656deb32.html?picindex=3
�ϽŲ�̫����