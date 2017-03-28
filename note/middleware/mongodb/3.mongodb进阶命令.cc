在处理之前先注意，当前操作是不是这个数据库

1.单个条件查询(选取集合有两种方式)
  db.product.find({"_id":ObjectId("581c422a9d30ff29e40d8e63")}).pretty()
  db.getCollection('product').find({"_id":ObjectId("581c422a9d30ff29e40d8e63")}).pretty()
  pretty()跟es用法一样，用来美化输出结果。