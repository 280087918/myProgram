���²�������ʹ�û����HttpRequestִ��
----------------------------------------



#�鿴��Ⱥ�еĽڵ�״̬
  GET http://192.168.22.181:9200/_cluster/state/nodes/

#��Ⱥ�е����нڵ㷢�͹ر�����(ûִ�гɹ�����ʾû�д����������ʽ��������_��ͷ)
  POST http://192.168.22.181:9200/_cluster/nodes/_shutdown/

#������һ����Ϊblog����������Ϊarticle�����͡�
  XPUT http://192.168.22.181:9200/blog/article/1
  {
   "title": "New version of Elasticsearch released!", 
   "content": "Version 1.0 released today!",
   "tags": ["announce","elasticsearch", "release"]
  }
 ���http://192.168.22.181:9200/_plugin/head/��ǩ����Ԥ�����Կ���
     ÿ���ĵ�����һ���������ƣ������������ͣ���Ȼ��������idȥ��λ�ĵ�
     ���������ס�Ϳ����ˣ�es���ĵ��ṹ��:��������/��������/����id

#��ȡ���洴�����ĵ�
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

#�ĵ��ĸ��µĹ���
  ��ȡ�ĵ�ɾ�������޸��ĵ���source��������
  XPOST http://192.168.22.181:9200/blog/article/1/_update
  {
    "script" : "ctx._source.content=\"new content\""
  }
  ע�⣺�����ĵ��󣬰汾�Ž������ı�(�汾�ŵ���)
  Ĭ�������es���ڰ�ȫ���ǲ�����̬�ű��ĸ��£���Ҫ��elasticsearch.yml�������������
  script.inline: on
  script.indexed: on
  script.file: on

#����һ���ı���û�е��ֶ�
  �����Ҫ����Ĭ��ֵ�������õ�upsert�ֽ�
  XPOST http://192.168.22.181:9200/blog/article/1/_update
  {
    "upsert" : {
	"counter" : 0
    },
    "script" : "ctx._source.counter += 1"
  }
  ��������Ѿ������ã���ʾ��ָ��
  ��������ſ���
  {
    "script" : "ctx._source.counter = 1"
  }

#ɾ��һ���ĵ�
 XDELETE http://192.168.22.181:9200/blog/article/1

#�鿴������mapping
http://192.168.11.211:9200/cims/commodity/_mapping?pretty