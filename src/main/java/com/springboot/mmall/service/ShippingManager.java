package com.springboot.mmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShippingManager {
	@Autowired
	private IShippingService shippingService ;
	
	public synchronized void updateShipping(Integer id){
		shippingService.updateUserId(id);
	}

}
