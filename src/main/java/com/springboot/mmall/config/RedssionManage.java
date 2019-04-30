package com.springboot.mmall.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * redssion分布式锁实现
 * @author yzhang98
 *
 */
@Configuration	
public class RedssionManage {
	@Value("${spring.redis.host}")
	private String redisIp ;
	@Value("${spring.redis.port}")
	private Integer redisPort ;
	@Value("${spring.redis.password}")
	private String password ;
	
	/**
	 * 创建Redisson
	 * @return
	 */
	@Bean
	public Redisson creatRedssion(){
		Config config = new Config();
		config.useSingleServer().setAddress(new StringBuffer().append("redis://").append(redisIp).append(":").append(redisPort).toString()).setPassword(password) ;
		Redisson redisson = (Redisson) Redisson.create(config);
		return redisson ;
	}

	public String getRedisIp() {
		return redisIp;
	}

	public void setRedisIp(String redisIp) {
		this.redisIp = redisIp;
	}

	

	public Integer getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(Integer redisPort) {
		this.redisPort = redisPort;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
