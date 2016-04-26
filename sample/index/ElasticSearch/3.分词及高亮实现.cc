准备
先实现ES分词，这里用的是ik分词

1.从git上拉取最新的ik分词
https://github.com/medcl/elasticsearch-analysis-ik

2.最好不要按照网上的设置去做，因为最新版本的分词安装与之前的版本会不一样
  拉取下来后，根据readme.md去一步一步实现

3.使用的时候分词器别名也不要根据网上的写ik
  因为新版本的分词器已经不存在ik了，分了两种
  `ik_smart` , `ik_max_word`

4.安装完后测试
  POST
  http://localhost:9200/_analyze?analyzer=ik_smart&text=环游世界一百天
  测试ik_smart分词器对于一般的字符串是怎么分词的

5.高亮测试
  这个不用安装插件，本身就是lunce的特性，所以ES可以轻松在上面做改造
  假设我们有一个索引名称为book,索引类型为tech.里面有个name的字段值为:环游世界一百天
  POST
  http://localhost:9200/book/tech/_search
{
    "query" : { "term" : { "name" : "环游世界" }},
    "highlight" : {
        "pre_tags" : ["<tag1>", "<tag2>"],
        "post_tags" : ["</tag1>", "</tag2>"],
        "fields" : {
            "name" : {}
        }
    }
}
  这样就可以看到高亮效果了

6.注意一下，看到高亮不是在源文档上面进行修改的，只是返回了一个高亮的字符串
  所以整合spring的话，要重写ResultMapper方法，一个一个值去指定