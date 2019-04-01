package com.springboot.mmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = {"com.springboot.mmall.dao"}) //需要扫描的mapper地址
@EnableCaching
public class MmallApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MmallApplication.class, args) ;
	}

}
