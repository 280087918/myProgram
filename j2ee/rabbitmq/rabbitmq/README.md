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
		
3.