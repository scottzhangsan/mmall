package com.springboot.mmall.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MQReceive {
	
	private static Logger logger = LoggerFactory.getLogger(MQReceive.class);
	
	//监听这个queue下的message信息。
	@RabbitListener(queues=MQconfig.QUEUE)
	public void receive(String message){
		logger.info("接受message,{}",message);
	}
	
	@RabbitListener(queues=MQconfig.TOPIC_QUEUE1)
	public void receiveTopic(String message){
	  logger.info(MQconfig.TOPIC_QUEUE1+"recive message is" + message);	
	}
	
	@RabbitListener(queues=MQconfig.TOPIC_QUEUE2)
	public void receiveTopic2(String message){
		logger.info(MQconfig.TOPIC_QUEUE2+"receive message is"+message);
	}
	
	@RabbitListener(queues=MQconfig.FANOUT_QUEUE1)
	public void receiveFanout1(String message){
		logger.info(message);
	}
	
	@RabbitListener(queues=MQconfig.FANOUT_QUEUE2)
	public void receiveFanout2(String message){
		logger.info(message);
	}

}
