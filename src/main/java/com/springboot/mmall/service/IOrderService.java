package com.springboot.mmall.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.vo.OrderVo;
import com.springboot.mmall.vo.ProductOrderItemVo;

public interface IOrderService {
	
	ServerResponse<Map<String, String>> pay(Long orderNo,Integer userId) ;
	
	@SuppressWarnings("rawtypes")
	ServerResponse queryOrderPayStatus(Long orderNo,Integer userId);
	
	//保存支付宝回调的信息的接口
	ServerResponse<String> saveAlipayInfo(Map<String, String> map)  ;
	
	@SuppressWarnings("rawtypes")
	ServerResponse createOrder(Integer userId,Integer shippingId) ;
	
	ServerResponse<ProductOrderItemVo> getProductOrderItem(Long orderNo) ;
	
	ServerResponse<PageInfo<OrderVo>> listByPage(Integer pageSize,Integer pageNum) ;
	
	ServerResponse<String> cancel(Long orderNo) ;
	
	

}
