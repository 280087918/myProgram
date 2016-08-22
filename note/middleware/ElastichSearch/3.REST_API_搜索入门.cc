背景:先创创建三个文档
XPOST http://192.168.22.181:9200/books/es/1
{"title":"Elasticsearch Server" ,"published": 2013}

XPOST http://192.168.22.181:9200/books/es/2
{"title":"Mastering Elasticsearch" ,"published": 2013}

XPOST http://192.168.22.181:9200/books/solr/1
{"title":"Apache Solr 4 Cookbook" ,"published": 2012}

注意观察这三个文档的数据，基本有以下特点
a)这三个文档的索引名称都是books
b)前两个文档的类型是es，第三个文档类型是solr
c)第三个的文档索引也是1，因为他跟前两个文档的类型不一样
d)published除了solr是2012，es的都是2013


#获取所有索引名称为books的文档的映射关系
 XGET http://192.168.22.181:9200/books/_mapping

#获取所有索引名称为books的文档
 XGET http://192.168.22.181:9200/books/_search

#有意思的地方来了，如果想获取索引名称为books，类型为es的所有文档的话，就这么处理
 XGET http://192.168.22.181:9200/books/es/_search

#那么不限定索引名称，搜索全部文档该如何处理呢？请看下面
 XGET http://192.168.22.181:9200/_search

到这里我觉得es的REST API更多的是约定俗成的方案，主要是要理解这里面的结构和机制

#如果想查询title包含某个关键词改如何请求呢？请参考下面语句
 XGET http://192.168.22.181:9200/_search?q=title:elasticsearch
 从返回结果的分数可以看到，关键词elasticsearch在title前面的分值要高很多

