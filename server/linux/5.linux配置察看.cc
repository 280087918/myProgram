1.�쿴��ϸ��cpu��Ϣ
  cat /proc/cpuinfo

2.�鿴����CPU����
  cat /proc/cpuinfo |grep "physical id"|sort |uniq|wc -l

3.�鿴�߼�CPU����
  cat /proc/cpuinfo |grep "processor"|wc -l

4.�鿴CPU�Ǽ���
  cat /proc/cpuinfo |grep "cores"|uniq

5.�鿴CPU��Ƶ
  cat /proc/cpuinfo |grep MHz|uniq

6.�鿴��ǰϵͳ�汾��Ϣ
  cat /etc/issue

7.�鿴�ڴ���Ϣ
  dmidecode -t memory

8.�鿴Ӳ����Ϣ
  cat /sys/block/sda/queue/rotational
  0��SSD ��̬Ӳ��
  1��HDD ��еӲ��