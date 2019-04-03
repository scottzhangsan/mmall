package com.springboot.mmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = {"com.springboot.mmall.dao"}) //需要扫描的mapper地址
@EnableCaching
@ServletComponentScan
@EnableScheduling
public class MmallApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MmallApplication.class, args) ;
	}

}
