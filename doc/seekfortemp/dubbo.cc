1.dubbo失败重试
  dubbo自身有失败重试的机制，但是特定的场景可能会有问题
  比如说新增数据，如果接口本身逻辑处理比较慢，或者网络传输慢的情况下，可能会导致数据的重复录入
  这个时候可以在服务端的具体服务声明中指定delay="-1"去关闭失败重试功能。

2.dubbo和dubbox区别
  dubbox支持REST风格远程调用（HTTP + JSON/XML)
  支持完全基于Java代码的Dubbo配置：基于Spring的Java Config，实现完全无XML的纯Java代码方式来配置dubbo

3.dubbo 数据传输原理
  http://blog.csdn.net/paul_wei2008/article/details/19355681