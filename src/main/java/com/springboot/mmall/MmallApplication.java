package com.springboot.mmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.springboot.mmall.dao"}) //需要扫描的mapper地址
public class MmallApplication {
	
	public static void main(String[] args) {
		//我是冲突，谢谢，请解决
		SpringApplication.run(MmallApplication.class, args) ;
	}

}
