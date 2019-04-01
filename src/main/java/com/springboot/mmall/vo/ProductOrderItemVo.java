package com.springboot.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

public class ProductOrderItemVo {
	
	
	private BigDecimal productTotalPrice ;
	
	private String imageHost ;
	
	private List<OrderItemVo> orderItemVoList ;

	public BigDecimal getProductTotalPrice() {
		return productTotalPrice;
	}

	public void setProductTotalPrice(BigDecimal productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}

	public String getImageHost() {
		return imageHost;
	}

	public void setImageHost(String imageHost) {
		this.imageHost = imageHost;
	}

	public List<OrderItemVo> getOrderItemVoList() {
		return orderItemVoList;
	}

	public void setOrderItemVoList(List<OrderItemVo> orderItemVoList) {
		this.orderItemVoList = orderItemVoList;
	}
	

}
