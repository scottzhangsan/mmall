package com.springboot.mmall;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTest {
	private static final Logger logger = LoggerFactory.getLogger(RabbitMQTest.class) ;
	@Autowired
	private AmqpTemplate amqpTemplate ;
	
	/**
	 * directExchange类型交换机发送消息。
	 */
	/*@org.junit.Test
	public void test01(){
		String message = "Hello World01";
		//参数分别是：交换机名称，路由key名称，发送的消息
		amqpTemplate.convertAndSend("direct.exchange", "queue01", message);
		logger.info("queue1 send message,{}",message);
	}*/
	
	/*@Test
	public void test02(){
		String message = "Hello TopicExchange" ;
		//结果：这个发送 topicQueue01和topicQueue02都能收到消息
		amqpTemplate.convertAndSend("testTopicExchange", "test.test", message);
		//结果：这个发送 topicQueue02都能收到消息
		amqpTemplate.convertAndSend("testTopicExchange", "test.test12", message);
	}*/
	
	@Test
	public void test03(){
		String message = "Hello FanoutExchange" ;
		//路由key的值可以不用写
		//结果预测，fanoutQueue01和fanoutQueue02队列里面都存储有消息
		amqpTemplate.convertAndSend("testFanoutExchange", "", message);
	}

}
