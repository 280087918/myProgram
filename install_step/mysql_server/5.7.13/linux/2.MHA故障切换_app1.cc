[server default]
#manager dir
manager_workdir=/masterha/app1
manager_log=/masterha/app1/manager.log
remote_workdir=/masterha/app1

#mysql manager user
user=root
password=ffzx2016

#node server user
ssh_user=root

#replication_user
repl_user=repluser
repl_password=123456

#checking master every second
ping_interval=1

#promote script
#shutdown_script=""
#master_ip_failover_script="/usr/local/bin/master_ip_failover"
#master_ip_online_change_script=""
#report_script=""

[server1]
hostname=192.168.1.98
master_binlog_dir="/data/mysql"
ssh_port=22
candidate_master=1

[server2]
hostname=192.168.1.81
master_binlog_dir="/data/mysql"
ssh_port=22
candidate_master=1

[server3]
hostname=192.168.1.84
master_binlog_dir="/data/mysql"
ssh_port=22
candidate_master=1