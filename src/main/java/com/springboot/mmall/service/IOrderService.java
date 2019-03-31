package com.springboot.mmall.service;

import java.util.Map;

import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.vo.OrderVo;

public interface IOrderService {
	
	ServerResponse<Map<String, String>> pay(Long orderNo,Integer userId) ;
	
	ServerResponse queryOrderPayStatus(Long orderNo,Integer userId);
	
	//保存支付宝回调的信息的接口
	ServerResponse<String> saveAlipayInfo(Map<String, String> map)  ;
	
	ServerResponse createOrder(Integer userId,Integer shippingId) ;
	
	

}
