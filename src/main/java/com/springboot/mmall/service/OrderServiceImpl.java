package com.springboot.mmall.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.dao.MmallOrderMapper;
@Service
public class OrderServiceImpl implements IOrderService {
	@Autowired
	private MmallOrderMapper orderMapper ;

	@Override
	public ServerResponse<Map<String, String>> pay(Long orderNo, Integer userId) {
		
		return null;
	}

}
