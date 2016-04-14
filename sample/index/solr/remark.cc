资源：
官网:http://lucene.apache.org/solr
选型:选6.0 下的版本，因为6.0的已经用上jdk8了，之前的用jdk7就可以了

1.根据系统的环境选择需要下载的版本(linux还是windows版本)

2.起停solr
  启动:进入bin目录，执行solr start -e cloud -noprompt,这样slor服务就启动起来了
  停止:solr stop -all

----由于工作原因，先停止研究