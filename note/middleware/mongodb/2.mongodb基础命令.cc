1.�鿴��ǰ���ݿ�
  show dbs

2.��ʾ��ǰ���ݿ�
  db

3.�л����ݿ�(��Ҫ�л���test���ݿ�)
  use test

4.�������ݿ�(������һ��)
  use cims

5.��������
  ���������䴴�������ݿ�֮��ִ��show dbs���ǲ��ܿ���cims���ݿ�ģ���Ҫ��cims�������һЩ���ݲſ���
  db.product.insert({"��Ʒ����":"��ţ"})
  ��MongoDB��Ĭ�ϵ����ݿ���test,���û�д������ݿ⣬��ô���϶��ᱣ����test���ݿ���
  ����һ��Ҫע����������product�Ǽ������ƣ����ĸ�db��ִ�оͻ����ĸ����ݿ�������product�������

6.ɾ������ǰ�����ݿ�
  ע�⣬�ǵ�ǰ���ݿ⣬������ִ��֮ǰ��һ��Ҫ�л���Ҫɾ�������ݿ���
  use cims
  db.dropDatabase()

7.ɾ������
  db.collection.drop()

8.��������
  db.product.insert({title: 'MongoDB �̳�', 
    description: 'MongoDB ��һ�� Nosql ���ݿ�',
    by: '����̳�',
    tags: ['mongodb', 'database', 'NoSQL'],
    likes: 100
  })

9.��ѯ�Ѳ�����ĵ�
  db.product.find()
  Ҳ����������������ʽ�ÿ���
  db.product.find().pretty()

10.Ҳ���Խ���Ҫ������ĵ�����Ϊһ����������ִ�в������
   document=({title: '��ի־��֮��Ů��', 
    description: '����һ�����Ӿ�',
    by: '������',
    tags: ['��ի', '��ի־��', '��Ů��'],
    likes: 100
   });
   db.product.insert(document)

11.�����ĵ�
   db.collection.update(
   <query>,
   <update>,
   {
     upsert: <boolean>,
     multi: <boolean>,
     writeConcern: <document>
   }
  )
  query:update��������������where
  update:update����һЩ��������
  upsert:��ѡ����������ڼ�¼�Ƿ�������ݣ�Ĭ��false��������
  multi:Ĭ��false������ҵ������͸���һ����¼��true�Ļ����Ǹ��¶�����¼
  writeConcern:�׳��쳣�ļ���

  �����������"MongoDB �̳�"���ĵ�
  db.product.update({title:'MongoDB �̳�'}, {$set:{title:'MongoDB'}})

12.ɾ���ĵ�
   �ṹ
   db.collection.remove(
   <query>,
   {
     justOne: <boolean>,
     writeConcern: <document>
   }
   )
   query: ɾ���ĵ�������
   justOne: ������Ϊtrue����1����˼������ҵ�������ֻɾ��һ��
   writeConcern: �׳��쳣�ļ���

   demo:
   db.product.remove({title:'MongoDB �̳�'})



