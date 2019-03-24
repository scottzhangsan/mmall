package com.springboot.mmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 前台商品信息接口
 * @author yzhang98
 *
 */

import com.github.pagehelper.PageInfo;
import com.springboot.mmall.bo.MmallProductBo;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.pojo.MmallProduct;
import com.springboot.mmall.service.IProductService;
@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private IProductService productService ;
	
	@RequestMapping("list")
	public ServerResponse<PageInfo<MmallProduct>> listByOrderPage(String productName,Integer categoryId,
			@RequestParam(value=" pageNum",defaultValue="1")Integer pageNum,@RequestParam(value="pageSize",defaultValue="10")Integer pageSize,String orderBy){
		
		return productService.listByOrderPage(productName, categoryId, pageNum, pageSize, orderBy) ;
	}
	
	@RequestMapping("detail")
	public ServerResponse<MmallProductBo> detail(Integer productId){
		return productService.detail(productId) ;
	}
	
	
	

}
