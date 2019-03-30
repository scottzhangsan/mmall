package com.springboot.mmall.service;

import java.util.Map;

import com.springboot.mmall.common.ServerResponse;

public interface IOrderService {
	
	ServerResponse<Map<String, String>> pay(Long orderNo,Integer userId) ;
	
	ServerResponse queryOrderPayStatus(Long orderNo,Integer userId);
	
	//保存支付宝回调的信息的接口
	ServerResponse<String> saveAlipayInfo(Map<String, String> map)  ;
	
	

}
