global_defs {  
   notification_email {  
         280087918@qq.com  
   }  
   notification_email_from 532250776@qq.com  
   smtp_server 192.168.1.9  
   smtp_connection_timeout 30
   router_id LVS_DEVEL  # 设置lvs的id，在一个网络内应该是唯一的
}  
vrrp_instance VI_1 {  
    state MASTER   #指定Keepalived的角色，MASTER为主，BACKUP为备          
    interface eth1  #指定Keepalived的角色，MASTER为主，BACKUP为备
    virtual_router_id 51  #虚拟路由编号，主备要一致
    priority 100  #定义优先级，数字越大，优先级越高，主DR必须大于备用DR    
    advert_int 1  #检查间隔，默认为1s
    authentication {  
        auth_type PASS  
        auth_pass 1111  
    }  
    virtual_ipaddress {  
        192.168.1.120  #定义虚拟IP(VIP)为192.168.2.33，可多设，每行一个
    }  
}  
# 定义对外提供服务的LVS的VIP以及port
virtual_server 192.168.1.120 80 {  
    delay_loop 6 # 设置健康检查时间，单位是秒                    
    lb_algo wrr # 设置负载调度的算法为wlc                   
    lb_kind DR # 设置LVS实现负载的机制，有NAT、TUN、DR三个模式   
    nat_mask 255.255.255.0                
    persistence_timeout 0          
    protocol TCP                  
    real_server 192.168.1.84 80 {  # 指定real server1的IP地址
        weight 3   # 配置节点权值，数字越大权重越高              
        TCP_CHECK {  
        connect_timeout 10         
        nb_get_retry 3  
        delay_before_retry 3  
        connect_port 80  
        }  
    }  
    real_server 192.168.1.85 80 {  # 指定real server2的IP地址
        weight 3  # 配置节点权值，数字越大权重越高  
        TCP_CHECK {
        connect_timeout 10
        nb_get_retry 3
        delay_before_retry 3
        connect_port 80
        }  
     }  
}