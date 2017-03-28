1.查看当前数据库
  show dbs

2.显示当前数据库
  db

3.切换数据库(如要切换到test数据库)
  use test

4.创建数据库(跟上面一样)
  use cims

5.插入数据
  用上面的语句创建了数据库之后执行show dbs还是不能看见cims数据库的，需要往cims里面插入一些数据才可以
  db.product.insert({"商品名称":"红牛"})
  在MongoDB中默认的数据库是test,如果没有创建数据库，那么集合都会保存在test数据库下
  还有一点要注意的是这里的product是集合名称，在哪个db下执行就会在哪个数据库下生成product这个集合

6.删除【当前】数据库
  注意，是当前数据库，所以在执行之前，一定要切换到要删除的数据库下
  use cims
  db.dropDatabase()

7.删除集合
  db.collection.drop()

8.插入数据
  db.product.insert({title: 'MongoDB 教程', 
    description: 'MongoDB 是一个 Nosql 数据库',
    by: '菜鸟教程',
    tags: ['mongodb', 'database', 'NoSQL'],
    likes: 100
  })

9.查询已插入的文档
  db.product.find()
  也可以让他看起来样式好看点
  db.product.find().pretty()

10.也可以将需要插入的文档定义为一个变量，再执行插入语句
   document=({title: '聊斋志异之奇女子', 
    description: '这是一部电视剧',
    by: '蒲松龄',
    tags: ['聊斋', '聊斋志异', '奇女子'],
    likes: 100
   });
   db.product.insert(document)

11.更新文档
   db.collection.update(
   <query>,
   <update>,
   {
     upsert: <boolean>,
     multi: <boolean>,
     writeConcern: <document>
   }
  )
  query:update的条件，类似于where
  update:update对象一些操作符。
  upsert:可选，如果不存在记录是否插入数据，默认false，不插入
  multi:默认false，如果找到多条就更新一条记录，true的话就是更新多条记录
  writeConcern:抛出异常的级别

  比如更新上面"MongoDB 教程"的文档
  db.product.update({title:'MongoDB 教程'}, {$set:{title:'MongoDB'}})

12.删除文档
   结构
   db.collection.remove(
   <query>,
   {
     justOne: <boolean>,
     writeConcern: <document>
   }
   )
   query: 删除文档的条件
   justOne: 可设置为true或者1，意思是如果找到多条就只删除一条
   writeConcern: 抛出异常的级别

   demo:
   db.product.remove({title:'MongoDB 教程'})



