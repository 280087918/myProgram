׼��
��ʵ��ES�ִʣ������õ���ik�ִ�

1.��git����ȡ���µ�ik�ִ�
https://github.com/medcl/elasticsearch-analysis-ik

2.��ò�Ҫ�������ϵ�����ȥ������Ϊ���°汾�ķִʰ�װ��֮ǰ�İ汾�᲻һ��
  ��ȡ�����󣬸���readme.mdȥһ��һ��ʵ��

3.ʹ�õ�ʱ��ִ�������Ҳ��Ҫ�������ϵ�дik
  ��Ϊ�°汾�ķִ����Ѿ�������ik�ˣ���������
  `ik_smart` , `ik_max_word`

4.��װ������
  POST
  http://localhost:9200/_analyze?analyzer=ik_smart&text=��������һ����
  ����ik_smart�ִ�������һ����ַ�������ô�ִʵ�

5.��������
  ������ð�װ������������lunce�����ԣ�����ES��������������������
  ����������һ����������Ϊbook,��������Ϊtech.�����и�name���ֶ�ֵΪ:��������һ����
  POST
  http://localhost:9200/book/tech/_search
{
    "query" : { "term" : { "name" : "��������" }},
    "highlight" : {
        "pre_tags" : ["<tag1>", "<tag2>"],
        "post_tags" : ["</tag1>", "</tag2>"],
        "fields" : {
            "name" : {}
        }
    }
}
  �����Ϳ��Կ�������Ч����

6.ע��һ�£���������������Դ�ĵ���������޸ĵģ�ֻ�Ƿ�����һ���������ַ���
  ��������spring�Ļ���Ҫ��дResultMapper������һ��һ��ֵȥָ��