1.察看详细的cpu信息
  cat /proc/cpuinfo

2.查看物理CPU个数
  cat /proc/cpuinfo |grep "physical id"|sort |uniq|wc -l

3.查看逻辑CPU个数
  cat /proc/cpuinfo |grep "processor"|wc -l

4.查看CPU是几核
  cat /proc/cpuinfo |grep "cores"|uniq

5.查看CPU主频
  cat /proc/cpuinfo |grep MHz|uniq

6.查看当前系统版本信息
  cat /etc/issue

7.查看内存信息
  dmidecode -t memory

8.查看硬盘信息
  cat /sys/block/sda/queue/rotational
  0：SSD 固态硬盘
  1：HDD 机械硬盘