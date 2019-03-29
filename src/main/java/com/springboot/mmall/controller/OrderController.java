package com.springboot.mmall.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.mmall.common.ServerResponse;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@RequestMapping("/pay")
	public ServerResponse<Map<String, String>> pay(HttpSession session,String orderNo,HttpServletRequest request){
		return null ;
	}
	
	

}
