package com.springboot.mmall.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class MQconfig {
	
	public static final String QUEUE="queue";
	
	public static final String TOPIC_QUEUE1 = "topic.queue1" ;
	
	public static final String TOPIC_QUEUE2 = "topic.queue2" ;
	
	public static final String TOPIC_EXCHANGE = "topicExchange" ;
	
	//topic的模式可以发送通配符
	public static final String ROUTING_KEY1 = "topic.key1" ;
	
	public static final String ROUTING_KEY2 = "topic.#" ;
	
	public static final String FANOUT_EXCHANGE = "fanoutExchange" ;
	
	public static final String FANOUT_QUEUE1 = "fanoutqueue1";
	
	public static final String FANOUT_QUEUE2 = "fanoutqueue2" ;
	
	
	/**
	 * 
	 * Direct交换机模式,默认的交换机模式
	 * 创建队列，保存消息
	 * @return
	 */
	@Bean
	public Queue queue(){
		return new Queue(QUEUE,true) ; //是否做消息的持久化
	}
	
	@Bean
	public Queue topicQueue1(){
	return new Queue(TOPIC_QUEUE1, true) ;	
	}
	
	@Bean
	public Queue topicQueue2(){
	return new Queue(TOPIC_QUEUE2,true) ;	
	}
	
	/**
	 * 创建topicExchange类型的交换机
	 * @return
	 */
	@Bean
    public TopicExchange topicExchange(){
    	return new TopicExchange(TOPIC_EXCHANGE) ;
    }
	/**
	 * topicExchange 交换机绑定的路由key为topic.key1
	 * 如果是topic.key1这种路由key的时候，绑定到 topicQueue1这Queue上。
	 * @return
	 */
	@Bean
	public Binding topicBinding1(){
		return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(ROUTING_KEY1) ;
	}
	
	/**
	 * topicExchange 交换机绑定的路由key为 topic.#
	 * 支持通配符的模式，# 0或者多个字符。
	 * 如果是topic.#这种路由key的时候，绑定到topicQueue2这个Queue上。
	 * @return
	 */
	@Bean
	public Binding topicBinding2(){
		return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(ROUTING_KEY2) ;
	}
	
	@Bean
	public Queue fanoutQueue1(){
		return new Queue(FANOUT_QUEUE1) ;
	}
	
	@Bean
	public Queue fanoutQueue2(){
		return new Queue(FANOUT_QUEUE2) ;
	}
	
	/**
	 * fanoutExchange的交换机，不用绑定路由key只要在这个类型的
	 * 交换机的队列中，都能收到信息
	 * @return
	 */
	@Bean
	public FanoutExchange fanoutExchange(){
	  return new FanoutExchange(FANOUT_EXCHANGE) ;	
	}
	
	/**
	 * 只需要绑定交换机和队列不需要路由key
	 * @return
	 */
	@Bean
	public Binding fanoutBinding1(){
		return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange()) ;
	}
	
	@Bean
	public Binding fanoutBinding2(){
		return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange()) ;
	}
}
