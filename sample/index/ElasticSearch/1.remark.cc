官网:www.elastic.co
可以到官网下下载最新的ES版本，并解压缩
目前用的版本号:elasticsearch-2.3.1


-----------------------------------环境准备-----------------------------------------------
1.下载监控中心【kibana】
  在elastic官网downloads页面有下载，本次下载的是kibana 4.5.0

2.下载控制台【Marvel】
  进入bin目录，并依次执行以下命令。官网也有描述怎么安装的

3.安装Marvel
  Marvel现在理解只是个监控中心可视化的，而具体分析是由分析器kibana执行，所以这两个要配套进行
  1)安装Marvel
  进入ESbin目录
  plugin install license
  plugin install marvel-agent

  2)安装kibana
  进入kibanabin目录
  kibana plugin --install elasticsearch/marvel/latest

4.curl命令行模拟post请求安装(可选)
  资源地址:http://curl.haxx.se/download/
  解压缩到一个目录
  设置环境变量:CURL_HOME
  在Path里面添加HOME目录的变量:%CURL_HOME%;

5.启动ES服务和监控中心
  进入ES的bin目录，直接运行elasticsearch.bat
  或者这样启动,进入bin目录执行 elasticsearch -d (-d是守护进程) -这种在windows下启动有问题

  进入kibana的bin目录，执行kibana

  如果没有安装CURL则直接打开浏览器，输入地址: http://localhost:9200
  如果安装了CURL则新开一个命令窗口，输入CURL http://localhost:9200
  如果看到了类似json格式的字符串输出，则表明ES启动成功

6.监控中心URL
  http://localhost:5601/app/marvel

7.关闭ES
  curl -XPOST http://localhost:9200/_shutdown
  关闭不了，目前只能直接关闭命令窗口

8.head安装
  上面的监控中心可以选择性的去安装，head比较有用，可以模拟索引的创建跟查询
  到ES的bin目录下执行plugin install mobz/elasticsearch-head
  启动ES后，在浏览器上输入:http://localhost:9200/_plugin/head/ 即可访问

9.火狐上安装http请求插件
  火狐插件上搜索并安装httpRequester
  有这个就可以不用curl了

-------------------------------END--------------------------------------------------------

-----------------------------------相关操作-----------------------------------------------
注意windows底下不认识单引号，要用双引号
1.检查集群的健康状况:curl "localhost:9200/_cat/health?v"

2.检查集群(cluster)下面的节点(nodes):curl "localhost:9200/_cat/nodes?v"

3.列举所有的目录(指的是所有索引):curl "localhost:9200/_cat/indices?v"

4.创建一个名为customer的索引:curl -XPUT "localhost:9200/customer?pretty"
      后面加一个pretty的参数是为了说明pretty-print一下返回的json结果

5.将一个document放到index里面
      放一个type为external,id为1的document
      curl -XPUT "localhost:9200/customer/external/1?pretty" -d "{'name':'johnathan'}"
      因为windows只人双引号的问题，所以这个执行不了,目前是用head插件去录入这个数据的

      不指定id也可以
      curl -XPUT "localhost:9200/customer/external?pretty" -d "{'name':'johnathan'}"
      这样ES会指定一个随机字符串作为id

6.获取刚刚放到上面的document
      curl -XGET "localhost:9200/customer/external/1?pretty"

7.删除一个索引
      curl -XDELETE "localhost:9200/customer?pretty"

8.文档更新
      文档更新很简单，跟新增文档一样，只要id一样，类型一样，数值不一样就会更新。如果id不一样，那么就是新增。
      更新的时候可以多添加元素

9.另一种更新
      使用_update参数
      curl -XGET "localhost:9200/customer/external/1/_update?pretty"
      按照文档上说的是可以添加元素，不过这个_update命令目前没有被测试通过。直接报错了

【下面操作都用火狐的插件完成】
10.批量操作,使用_bulk API
      批量插入两条数据,请求的url:localhost:9200/customer/external/_bulk?pretty
      请求参数:
	{"index":{"_id":"1"}}
	{"name": "John Doe" }
	{"index":{"_id":"2"}}
	{"name": "Jane Doe" }
      描述，插入id为1和id为2的两条数据,只插入了一条数据，也没报错，先不管了。基于REST的就这样了。
      
-------------------------------END--------------------------------------------------------

-----------------------------------相关操作-----------------------------------------------
1.集群名称配置(cluster name)
  文件:config/elasticsearch.yml
  将名称的备注打开，并填写自己的集群名称

2.修改节点名称和客户端引用的id(node name)
  文件:config/elasticsearch.yml
  将相关节点信息备注打开并修改。

3.其他省略
  基本上看着elasticsearch.yml文件去设置相应的参数就好了
-------------------------------END--------------------------------------------------------

-----------------------------------注意事项-----------------------------------------------
1.同一个索引下的所有文档对于相同的字段，类型必须一致
  比如title这个信息可能存在不同的文档类型中，但是他们的类型都要保持一致，比如是title的都要String
-------------------------------END--------------------------------------------------------

-----------------------------------一些操作-----------------------------------------------
1.查看某个索引的段信息
  http://localhost:9200/car/_segments
-------------------------------END--------------------------------------------------------