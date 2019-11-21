package com.springboot.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

public class CartVo {
	
	private Boolean allChecked ;
	private List<CartProductVo> cartProductVoList ;
	private BigDecimal cartTotalPrice ;
	public Boolean getAllChecked() {
		return allChecked;
	}
	public void setAllChecked(Boolean allChecked) {
		this.allChecked = allChecked;
	}
	public List<CartProductVo> getCartProductVoList() {
		return cartProductVoList;
	}
	public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
		this.cartProductVoList = cartProductVoList;
	}
	public BigDecimal getCartTotalPrice() {
		return cartTotalPrice;
	}
	public void setCartTotalPrice(BigDecimal cartTotalPrice) {
		this.cartTotalPrice = cartTotalPrice;
	}
	
	
	 

}
