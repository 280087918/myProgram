����:www.elastic.co
���Ե��������������µ�ES�汾������ѹ��
Ŀǰ�õİ汾��:elasticsearch-2.3.1


-----------------------------------����׼��-----------------------------------------------
1.���ؼ�����ġ�kibana��
  ��elastic����downloadsҳ�������أ��������ص���kibana 4.5.0

2.���ؿ���̨��Marvel��
  ����binĿ¼��������ִ�������������Ҳ��������ô��װ��

3.��װMarvel
  Marvel�������ֻ�Ǹ�������Ŀ��ӻ��ģ�������������ɷ�����kibanaִ�У�����������Ҫ���׽���
  1)��װMarvel
  ����ESbinĿ¼
  plugin install license
  plugin install marvel-agent

  2)��װkibana
  ����kibanabinĿ¼
  kibana plugin --install elasticsearch/marvel/latest

4.curl������ģ��post����װ(��ѡ)
  ��Դ��ַ:http://curl.haxx.se/download/
  ��ѹ����һ��Ŀ¼
  ���û�������:CURL_HOME
  ��Path�������HOMEĿ¼�ı���:%CURL_HOME%;

5.����ES����ͼ������
  ����ES��binĿ¼��ֱ������elasticsearch.bat
  ������������,����binĿ¼ִ�� elasticsearch -d (-d���ػ�����) -������windows������������

  ����kibana��binĿ¼��ִ��kibana

  ���û�а�װCURL��ֱ�Ӵ�������������ַ: http://localhost:9200
  �����װ��CURL���¿�һ������ڣ�����CURL http://localhost:9200
  �������������json��ʽ���ַ�������������ES�����ɹ�

6.�������URL
  http://localhost:5601/app/marvel

7.�ر�ES
  curl -XPOST http://localhost:9200/_shutdown
  �رղ��ˣ�Ŀǰֻ��ֱ�ӹر������

8.head��װ
  ����ļ�����Ŀ���ѡ���Ե�ȥ��װ��head�Ƚ����ã�����ģ�������Ĵ�������ѯ
  ��ES��binĿ¼��ִ��plugin install mobz/elasticsearch-head
  ����ES���������������:http://localhost:9200/_plugin/head/ ���ɷ���

9.����ϰ�װhttp������
  ����������������װhttpRequester
  ������Ϳ��Բ���curl��

-------------------------------END--------------------------------------------------------

-----------------------------------��ز���-----------------------------------------------
ע��windows���²���ʶ�����ţ�Ҫ��˫����
1.��鼯Ⱥ�Ľ���״��:curl "localhost:9200/_cat/health?v"

2.��鼯Ⱥ(cluster)����Ľڵ�(nodes):curl "localhost:9200/_cat/nodes?v"

3.�о����е�Ŀ¼(ָ������������):curl "localhost:9200/_cat/indices?v"

4.����һ����Ϊcustomer������:curl -XPUT "localhost:9200/customer?pretty"
      �����һ��pretty�Ĳ�����Ϊ��˵��pretty-printһ�·��ص�json���

5.��һ��document�ŵ�index����
      ��һ��typeΪexternal,idΪ1��document
      curl -XPUT "localhost:9200/customer/external/1?pretty" -d "{'name':'johnathan'}"
      ��Ϊwindowsֻ��˫���ŵ����⣬�������ִ�в���,Ŀǰ����head���ȥ¼��������ݵ�

      ��ָ��idҲ����
      curl -XPUT "localhost:9200/customer/external?pretty" -d "{'name':'johnathan'}"
      ����ES��ָ��һ������ַ�����Ϊid

6.��ȡ�ոշŵ������document
      curl -XGET "localhost:9200/customer/external/1?pretty"

7.ɾ��һ������
      curl -XDELETE "localhost:9200/customer?pretty"

8.�ĵ�����
      �ĵ����ºܼ򵥣��������ĵ�һ����ֻҪidһ��������һ������ֵ��һ���ͻ���¡����id��һ������ô����������
      ���µ�ʱ����Զ����Ԫ��

9.��һ�ָ���
      ʹ��_update����
      curl -XGET "localhost:9200/customer/external/1/_update?pretty"
      �����ĵ���˵���ǿ������Ԫ�أ��������_update����Ŀǰû�б�����ͨ����ֱ�ӱ�����

������������û���Ĳ����ɡ�
10.��������,ʹ��_bulk API
      ����������������,�����url:localhost:9200/customer/external/_bulk?pretty
      �������:
	{"index":{"_id":"1"}}
	{"name": "John Doe" }
	{"index":{"_id":"2"}}
	{"name": "Jane Doe" }
      ����������idΪ1��idΪ2����������,ֻ������һ�����ݣ�Ҳû�����Ȳ����ˡ�����REST�ľ������ˡ�
      
-------------------------------END--------------------------------------------------------

-----------------------------------��ز���-----------------------------------------------
1.��Ⱥ��������(cluster name)
  �ļ�:config/elasticsearch.yml
  �����Ƶı�ע�򿪣�����д�Լ��ļ�Ⱥ����

2.�޸Ľڵ����ƺͿͻ������õ�id(node name)
  �ļ�:config/elasticsearch.yml
  ����ؽڵ���Ϣ��ע�򿪲��޸ġ�

3.����ʡ��
  �����Ͽ���elasticsearch.yml�ļ�ȥ������Ӧ�Ĳ����ͺ���
-------------------------------END--------------------------------------------------------

-----------------------------------ע������-----------------------------------------------
1.ͬһ�������µ������ĵ�������ͬ���ֶΣ����ͱ���һ��
  ����title�����Ϣ���ܴ��ڲ�ͬ���ĵ������У��������ǵ����Ͷ�Ҫ����һ�£�������title�Ķ�ҪString
-------------------------------END--------------------------------------------------------

-----------------------------------һЩ����-----------------------------------------------
1.�鿴ĳ�������Ķ���Ϣ
  http://localhost:9200/car/_segments
-------------------------------END--------------------------------------------------------