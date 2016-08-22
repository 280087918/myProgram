1.火狐安装插件:HttpRequester

2.没安装分词器之前
  http://192.168.22.181:9200/_analyze?text=美国留给伊拉克的是个烂摊子吗
  method=geet
  可以看到默认的分词器将每个中文都拆开了，显然无法使用

3.ik分词器安装
  git下载
  https://github.com/medcl/elasticsearch-analysis-ik
  cmd到根目录并执行
  mvn package
  将elasticsearch-analysis-ik\target\releases\elasticsearch-analysis-ik-1.9.4.zip上传到服务器

  root用户操作
  创建ik目录/usr/local/elasticsearch-2.3.4/plugins/ik

  将分词器复制到ik目录并直接解压缩
  cp elasticsearch-analysis-ik-1.9.4.zip /usr/local/elasticsearch-2.3.4/plugins/ik
  cd /usr/local/elasticsearch-2.3.4/plugins/ik
  unzip elasticsearch-analysis-ik-1.9.4.zip

  重启es,记得切换用户

4.这个时候看到分词已经达到了想要的效果
  http://192.168.22.181:9200/_analyze?analyzer=ik_smart&text=美国留给伊拉克的是个烂摊子吗

5.建立好了文档
  Product(indexName=product,type=local,id=id)
  用HttpRequest的post方法
  http://192.168.22.181:9200/product/local/_search
  {
	"query" : {
		"query_string" : { "query" : "name:三星" }
	}
  }

7.结合springdata的测试
  http://192.168.22.181:9200/product/local/_search
  {
    "query" : {
      "bool" : {"should" : [{"term" : {"name" : "三星"}}]}}
    }
  }

8.从第一个文档开始，返回2个文档
  http://192.168.22.181:9200/product/local/_search
  {
    "from" : 0,
    "size" : 2,
    "query" : {
      "bool" : {"should" : [{"term" : {"name" : "三星"}}]}}
    }
  }

  这样也可以
  {
    "query" : {
      "bool" : {"should" : [{"term" : {"name" : "三星"}}]}},
     "from" : 1,
     "size" : 2
    }
  }