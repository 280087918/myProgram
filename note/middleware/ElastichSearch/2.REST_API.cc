以下操作可以使用火狐的HttpRequest执行
----------------------------------------



#查看集群中的节点状态
  GET http://192.168.22.181:9200/_cluster/state/nodes/

#向集群中的所有节点发送关闭请求(没执行成功，提示没有错误的索引格式，不能用_开头)
  POST http://192.168.22.181:9200/_cluster/nodes/_shutdown/

#“创建一个名为blog的索引，名为article的类型”
  XPUT http://192.168.22.181:9200/blog/article/1
  {
   "title": "New version of Elasticsearch released!", 
   "content": "Version 1.0 released today!",
   "tags": ["announce","elasticsearch", "release"]
  }
 结合http://192.168.22.181:9200/_plugin/head/标签数据预览可以看见
     每个文档都有一个索引名称，还有索引类型，当然还有索引id去定位文档
     所以这里记住就可以了，es的文档结构是:索引名称/索引类型/索引id

#获取上面创建的文档
  XGET http://192.168.22.181:9200/blog/article/1
  {
    "_index": "blog",
    "_type": "article",
    "_id": "1",
    "_version": 1,
    "found": true,
    "_source": {
        "title": "New version of Elasticsearch released!",
        "content": "Version 1.0 released today!",
        "tags": [
            "announce",
            "elasticsearch",
            "release"
        ]
     }
  }

#文档的更新的过程
  获取文档删除，并修改文档的source部分内容
  XPOST http://192.168.22.181:9200/blog/article/1/_update
  {
    "script" : "ctx._source.content=\"new content\""
  }
  注意：更新文档后，版本号将发生改变(版本号递增)
  默认情况下es出于安全考虑不允许动态脚本的更新，需要在elasticsearch.yml中添加以下配置
  script.inline: on
  script.indexed: on
  script.file: on

#新增一个文本中没有的字段
  如果需要给定默认值，可以用到upsert字节
  XPOST http://192.168.22.181:9200/blog/article/1/_update
  {
    "upsert" : {
	"counter" : 0
    },
    "script" : "ctx._source.counter += 1"
  }
  上面这个已经不能用，提示空指针
  下面这个才可以
  {
    "script" : "ctx._source.counter = 1"
  }

#删除一个文档
 XDELETE http://192.168.22.181:9200/blog/article/1

#查看索引的mapping
http://192.168.11.211:9200/cims/commodity/_mapping?pretty