�ο�����:
http://www.yiibai.com/mongodb/mongodb_quick_guide.html
1.��mongodb����������mongodb��װ��
  http://www.mongodb.org/downloads
  ��վ���ṩ����url�������ֱ�Ӿ�wget��

2.���ļ����ص�/usr/local/soft��
  mongodb-linux-x86_64-3.4.2.tgz
  ����ѹ
  tar -xzvf mongodb-linux-x86_64-3.4.2.tgz

3.���ļ��Ƶ���һ��Ŀ¼
  mv mongodb-linux-x86_64-3.4.2 /usr/local
  cd /usr/local
  mv mongodb-linux-x86_64-3.4.2 mongodb-3.4.2

4.����dataĿ¼��logĿ¼
  cd /usr/local/mongodb-3.4.2
  mkdir -p /mongo/data/db /mongo/logs

5.ǰִ̨��
  ./bin/mongod -dbpath=/mongo/data

6.��ִ̨��
  �ֶ�����mongod.log
  cd /mongo/logs
  vim mongod.log
  ./mongod -dbpath=/mongo/data -logpath=/mongo/logs/mongod.log --fork

7.�رշ���
  ./mongod -dbpath=/mongo/data -logpath=/mongo/logs/mongod.log --shutdown

8.�����쳣
  ERROR: child process failed, exited with error number 1
  ����˵��֮ǰ��mongo����û����ȷ�ر�
  ����������Ŀǰ�����Ĳ�һ��

  