package com.springboot.mmall.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.springboot.mmall.util.JsonUtil;

@Component
public class MQSender {
	
	private static final Logger logger = LoggerFactory.getLogger(MQSender.class) ;
	@Autowired
	private AmqpTemplate amqpTemplate ;
	
	/**
	 * rabbitMQ不指定交换机，默认使用的是点对点的交换机，
	 * 也就是只要相同的queue能收到相应的消息。
	 * The default exchange is implicity bound to every queue ,with a 
	 * 
	 * routing key equal to the queue name . It is not possible to explicity
	 * 
	 * bind to, or unbind from the default exchange . It also cannot be deleted .
	 * 
	 * 默认交换器隐式地绑定到每个队列，其路由键等于队列名称，无法显示绑定到默认
	 * 交换器或默认交换器解绑定。它也不能被删除。
	 * @param object
	 */
	public void send(Object object){
		String mesage = JsonUtil.object2String(object);
		amqpTemplate.convertAndSend(MQconfig.QUEUE, mesage);
		logger.info("queue1 send message,{}",mesage);
	}
	
	public void sendTopicMessage(){
		String msg = "topicMessage" ;
		amqpTemplate.convertAndSend(MQconfig.TOPIC_EXCHANGE, MQconfig.ROUTING_KEY1, msg+"routing key is"+MQconfig.ROUTING_KEY1);
	    logger.info("XXXQueue send message,{}"+msg);
	}
	
	
	public void sendTopicMessage2(){
		String msg = "topicMessage2" ;
		amqpTemplate.convertAndSend(MQconfig.TOPIC_EXCHANGE, MQconfig.ROUTING_KEY2, msg+"routing key is" +MQconfig.ROUTING_KEY2);
		logger.info("XXXQueue send message,{}",msg);
	}
	
	/**
	 * 广播类型的交换机， 绑定了2个queue， fanoutqueue1 , fanoutqueue2.这两个
	 * 队列都能收到相应的信息
	 */
	public void sendFanoutMessage1(){
		String message = "hello fanoutExchange" ;	
		amqpTemplate.convertAndSend(MQconfig.FANOUT_EXCHANGE, "", message);
		logger.info("this is fanout Exchange send message");
	}

}
