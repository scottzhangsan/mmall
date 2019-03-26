package com.springboot.mmall.service;

import java.util.Map;

import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.pojo.MmallShipping;

public interface IShippingService {
	
	public ServerResponse<Map<String, Integer>> add(MmallShipping shipping) ;
	
	ServerResponse<String> delete(Integer shipping,Integer userId) ;
	
	ServerResponse<String> update(MmallShipping shipping,Integer userId) ;

}
