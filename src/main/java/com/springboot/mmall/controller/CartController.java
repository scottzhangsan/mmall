package com.springboot.mmall.controller;

import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.service.ICartService;
import com.springboot.mmall.vo.CartVo;

@RestController
@RequestMapping("cart")
public class CartController {
	@Autowired
	private ICartService cartService ;
	/**
	 * 查询购物车
	 * @param session
	 * @return
	 */
	@RequestMapping("list")
	public ServerResponse<CartVo> list(HttpSession session){
		return cartService.list(null) ;
	}
	/**
	 * 添加购物车
	 * @param session
	 * @param productId
	 * @param count
	 * @return
	 */
	@RequestMapping("add")
	public ServerResponse<CartVo> add(HttpSession session,Integer productId,Integer count){
		return cartService.add(null, productId, count) ;
	}
	/**
	 * 更新购物车商品数量
	 * @param session
	 * @param productId
	 * @param count
	 * @return
	 */
	@RequestMapping("update")
	public ServerResponse<CartVo> updateProductCount(HttpSession session,Integer productId,Integer count){
		return cartService.updateProductCount(null, productId, count);
	}
	/**
	 * 删除购物车中的商品
	 * @param session
	 * @param productIds
	 * @return
	 */
	@RequestMapping("delete_product")
	public ServerResponse<CartVo> deleteProduct(HttpSession session,String productIds){
		return cartService.removeProduct(null, productIds) ;
	}
	/**
	 * 选中购物车中的商品
	 * @param session
	 * @param productId
	 * @return
	 */
	@RequestMapping("select")
	public ServerResponse<CartVo> select(HttpSession session,Integer productId){
		return cartService.select(null, productId) ;
	}
	/**
	 * 取消选中商品
	 * @param session
	 * @param productId
	 * @return
	 */
	@RequestMapping("unselect")
	public ServerResponse<CartVo> unselect(HttpSession session,Integer productId){
		return cartService.unSelect(null, productId);
	}
	
	/**
	 * 查询购物车商品数量
	 * @param session
	 * @return
	 */
	@RequestMapping("get_cart_product_count")
	public ServerResponse<Long> countProduct(HttpSession session){
		return cartService.countProduct(null) ;
	}
	/**
	 * 全选
	 * @param session
	 * @return
	 */
	@RequestMapping("select_all")
	public ServerResponse<CartVo> selectAll(HttpSession session){
		return cartService.selectAll(null) ;
	}
	
	/**
	 * 全选
	 * @param session
	 * @return
	 */
	@RequestMapping("un_select_all")
	public ServerResponse<CartVo> unSelectAll(HttpSession session){
		return cartService.unSelectAll(null) ;
	}
	

	
	
}
