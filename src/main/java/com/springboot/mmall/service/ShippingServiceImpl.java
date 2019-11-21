package com.springboot.mmall.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.dao.MmallShippingMapper;
import com.springboot.mmall.pojo.MmallShipping;

@Service
public class ShippingServiceImpl implements IShippingService{
	
	@Autowired
	private MmallShippingMapper shippingMapper ;

	@Override
	@Transactional
	public ServerResponse<Map<String, Integer>> add(MmallShipping shipping) {
		shipping.setCreateTime(new Date());
		shipping.setUpdateTime(new Date());
    	int result = shippingMapper.insert(shipping);
    	Map<String, Integer> map = Maps.newHashMap() ;
    	if (result == 1) {
    		map.put("shippingId", shipping.getId()) ;
			return  ServerResponse.createBySuccess("新建地址成功",map) ;
		}
		return  ServerResponse.createByErrorMessage("新建地址失败") ;
	}

	@Override
	@Transactional
	public ServerResponse<String> delete(Integer shipping,Integer userId) {
		userId =22 ;
		int result = shippingMapper.deleteByPrimaryKey(shipping,userId) ;
		if (result == 1) {
			return ServerResponse.createBySuccessMessage("删除收货地址成功") ;
		}
		return ServerResponse.createBySuccessMessage("删除收货地址失败");
	}

	/**
	 * 更新收货地址放置横向越权
	 */
	@Override
	@Transactional
	public ServerResponse<String> update(MmallShipping shipping ,Integer userId ) {
		userId = 1 ;
		shipping.setUserId(userId);
		int result = shippingMapper.updateByPrimaryKey(shipping) ;
		if (result == 1) {
			return ServerResponse.createBySuccessMessage("更新收货地址成功") ;
		}
		return ServerResponse.createByErrorMessage("更新收货地址失败");
	}
	
	@Transactional
	public  void updateUserId(Integer id){
		MmallShipping shipping = shippingMapper.selectByPrimaryKey(id);
		shipping.setUserId(shipping.getUserId()+1);
		shippingMapper.updateByPrimaryKey(shipping) ;
	}

}
