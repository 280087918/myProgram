package com.john.test.b.workerqueue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;



import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 任务处理测试-发送
 * @author zhang.hc
 * @date 2016年6月7日 下午4:54:40
 */
public class NewTask {
	
	final static String QUEUE_NAME = "my_queue";
	static Connection connection = null;
	static Channel channel = null;
	
	public static void main(String[] argv) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();//新建一个链接工厂
		factory.setHost("192.168.1.78");//设置rabbit服务地址
		connection = factory.newConnection();//连接工厂根据上面的ip地址新建一个rabbitmq链接
		channel = connection.createChannel();//创建一个通道

		/*
		 * 第二个参数是消息是否持久化，就是是否让broker持久化到磁盘,如果broker宕机，这里设置了true，那么重启之后消息还会在
		 * 	这个queueDeclare的更改要生产者和消费者都同时修改成一样的才生效
		 * 设置完了这个还要在消息发布的时候指定消息的类型
		 *	channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes()); 
		 */
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);//通道声明要处理的队列名称
		
		String message = getMessage(argv);
		
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());//通道里面发送消息
		System.out.println(" [x] Sent '" + message + "'");
	}
	
	private static String getMessage(String[] strings){
	    if (strings.length < 1)
	        return "incoming message....";
	    return joinStrings(strings, " ");
	}

	private static String joinStrings(String[] strings, String delimiter) {
	    int length = strings.length;
	    if (length == 0) return "";
	    StringBuilder words = new StringBuilder(strings[0]);
	    for (int i = 1; i < length; i++) {
	        words.append(delimiter).append(strings[i]);
	    }
	    return words.toString();
	}

	@Override
	protected void finalize() throws Throwable {//关闭资源
		if(null != channel) {
			channel.close();
		}
		if(null != connection) {
			connection.close();
		}
		super.finalize();
	}
}
