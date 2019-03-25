package com.springboot.mmall.service;

import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.vo.CartVo;

public interface ICartService {
	
	ServerResponse<CartVo> add(Integer userId ,Integer productId,Integer count) ;
	/**
	 * 更新购物车商品的数量
	 * @param userId
	 * @param productId
	 * @param count
	 * @return
	 */
	ServerResponse<CartVo> updateProductCount(Integer userId ,Integer productId,Integer count) ;
	/**
	 * 移除购物车中的商品
	 * @param productIds
	 * @return
	 */
	ServerResponse<CartVo> removeProduct(Integer userId,String productIds) ;
	
	/**
	 * 查询购物车
	 * @param userId
	 * @return
	 */
	ServerResponse<CartVo> list(Integer userId);
	/**
	 * 选中某个商品
	 * @param productId
	 * @return
	 */
	ServerResponse<CartVo> select(Integer userId,Integer productId) ;
	/**
	 * 取消选中某个商品
	 * @param userId
	 * @param productId
	 * @return
	 */
	ServerResponse<CartVo> unSelect(Integer userId,Integer productId);
	/**
	 * 查询购物车中商品数量
	 * @param userId
	 * @return
	 */
	ServerResponse<Long> countProduct(Integer userId);
	/**
	 * 购物车全选
	 * @param userId
	 * @return
	 */
	ServerResponse<CartVo> selectAll(Integer userId) ;
	
	/**
	 * 购物车取消全选
	 * @param userId
	 * @return
	 */
	ServerResponse<CartVo> unSelectAll(Integer userId) ;

}
