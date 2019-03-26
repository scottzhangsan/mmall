package com.springboot.mmall.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 收货地址相关接口
 * @author yzhang98
 *
 */

import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.pojo.MmallShipping;
import com.springboot.mmall.service.IShippingService;
@RestController
@RequestMapping("/shipping")
public class ShippingController {
	
	@Autowired
	private IShippingService shippingService ;
	
	@RequestMapping("/add")
	public ServerResponse<Map<String, Integer>> addShipping(MmallShipping shipping,HttpSession session){
		return shippingService.add(shipping) ;
	}
	
	@RequestMapping("/update")
    public ServerResponse<String> update(MmallShipping shipping,HttpSession session){
	   return shippingService.update(shipping, null) ;
    }
	@RequestMapping("/delete")
	public ServerResponse<String> delete(Integer id,HttpSession session){
		return shippingService.delete(id,null) ;
	}
}
