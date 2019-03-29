package com.springboot.mmall.service;

import java.util.Map;

import com.springboot.mmall.common.ServerResponse;

public interface IOrderService {
	
	ServerResponse<Map<String, String>> pay(Long orderNo,Integer userId) ;

}
