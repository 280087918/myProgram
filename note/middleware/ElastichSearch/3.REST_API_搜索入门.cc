����:�ȴ����������ĵ�
XPOST http://192.168.22.181:9200/books/es/1
{"title":"Elasticsearch Server" ,"published": 2013}

XPOST http://192.168.22.181:9200/books/es/2
{"title":"Mastering Elasticsearch" ,"published": 2013}

XPOST http://192.168.22.181:9200/books/solr/1
{"title":"Apache Solr 4 Cookbook" ,"published": 2012}

ע��۲��������ĵ������ݣ������������ص�
a)�������ĵ����������ƶ���books
b)ǰ�����ĵ���������es���������ĵ�������solr
c)���������ĵ�����Ҳ��1����Ϊ����ǰ�����ĵ������Ͳ�һ��
d)published����solr��2012��es�Ķ���2013


#��ȡ������������Ϊbooks���ĵ���ӳ���ϵ
 XGET http://192.168.22.181:9200/books/_mapping

#��ȡ������������Ϊbooks���ĵ�
 XGET http://192.168.22.181:9200/books/_search

#����˼�ĵط����ˣ�������ȡ��������Ϊbooks������Ϊes�������ĵ��Ļ�������ô����
 XGET http://192.168.22.181:9200/books/es/_search

#��ô���޶��������ƣ�����ȫ���ĵ�����δ����أ��뿴����
 XGET http://192.168.22.181:9200/_search

�������Ҿ���es��REST API�������Լ���׳ɵķ�������Ҫ��Ҫ���������Ľṹ�ͻ���

#������ѯtitle����ĳ���ؼ��ʸ���������أ���ο��������
 XGET http://192.168.22.181:9200/_search?q=title:elasticsearch
 �ӷ��ؽ���ķ������Կ������ؼ���elasticsearch��titleǰ��ķ�ֵҪ�ߺܶ�

