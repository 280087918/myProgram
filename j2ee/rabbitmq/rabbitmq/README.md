2016-6-6
目前这个只是rabbitmq一个简单的hello world测试
其他如queue,subscribe,routing,topics,rpc后续再进行测试记录

2016-6-7
1.AMQP协议是一个高级抽象层消息通信协议，RabbitMQ是AMQP协议的实现。

2.重新组装了一下

	com.john.test.simple
		是简单的发送消息，接收消息的demo
		
	com.john.test.workerqueue
		rabbitMq可以多个消费者对应一个生产者的队列，rabbitMq是均匀分发的，好比这条消息发送给消费者1，下一条消息就会发送给消费者2
		这个demo重点解决的问题是，如果一个消息到达某一个消费者，那个消费者宕掉了，按照之前的逻辑，消息就没有了。
		解决这个问题就是在处理消息时不着急给broker发送回执，等消息主体处理完了之后才给broker收到消息的回执。
		万一这个消费者真宕掉了，broker就会把这个消息给另外的消费者处理。
		
3.exchange的fanout广播模式
	只要是连上这个exchange的客户端都能消费到消息，断开连接的客户端不能收到消息
	
4.exchange的direct模式
	与fanout广播模式不一样的是，消息的流向是根据路由(routing key)去走的，是直接的(direct)
	
5.exchange的topic模式其实跟direct模式区别不大，唯一的就是灵活，可根据规则去判定路由消息的去向
    *占位符只能代表一个单词
    #占位符可代表多个单词
	topic模式可以看作是direct模式的特殊形式
	