1.�����װ���:HttpRequester

2.û��װ�ִ���֮ǰ
  http://192.168.22.181:9200/_analyze?text=�������������˵��Ǹ���̯����
  method=geet
  ���Կ���Ĭ�ϵķִ�����ÿ�����Ķ����ˣ���Ȼ�޷�ʹ��

3.ik�ִ�����װ
  git����
  https://github.com/medcl/elasticsearch-analysis-ik
  cmd����Ŀ¼��ִ��
  mvn package
  ��elasticsearch-analysis-ik\target\releases\elasticsearch-analysis-ik-1.9.4.zip�ϴ���������

  root�û�����
  ����ikĿ¼/usr/local/elasticsearch-2.3.4/plugins/ik

  ���ִ������Ƶ�ikĿ¼��ֱ�ӽ�ѹ��
  cp elasticsearch-analysis-ik-1.9.4.zip /usr/local/elasticsearch-2.3.4/plugins/ik
  cd /usr/local/elasticsearch-2.3.4/plugins/ik
  unzip elasticsearch-analysis-ik-1.9.4.zip

  ����es,�ǵ��л��û�

4.���ʱ�򿴵��ִ��Ѿ��ﵽ����Ҫ��Ч��
  http://192.168.22.181:9200/_analyze?analyzer=ik_smart&text=�������������˵��Ǹ���̯����

5.���������ĵ�
  Product(indexName=product,type=local,id=id)
  ��HttpRequest��post����
  http://192.168.22.181:9200/product/local/_search
  {
	"query" : {
		"query_string" : { "query" : "name:����" }
	}
  }

7.���springdata�Ĳ���
  http://192.168.22.181:9200/product/local/_search
  {
    "query" : {
      "bool" : {"should" : [{"term" : {"name" : "����"}}]}}
    }
  }

8.�ӵ�һ���ĵ���ʼ������2���ĵ�
  http://192.168.22.181:9200/product/local/_search
  {
    "from" : 0,
    "size" : 2,
    "query" : {
      "bool" : {"should" : [{"term" : {"name" : "����"}}]}}
    }
  }

  ����Ҳ����
  {
    "query" : {
      "bool" : {"should" : [{"term" : {"name" : "����"}}]}},
     "from" : 1,
     "size" : 2
    }
  }