package com.springboot.mmall.service;


import com.github.pagehelper.PageInfo;
import com.springboot.mmall.bo.MmallProductBo;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.pojo.MmallProduct;

public interface IProductService {
	
	ServerResponse<PageInfo<MmallProduct>> listByPage(Integer pageNum,Integer pageSize) ;
	
	ServerResponse<PageInfo<MmallProduct>> serarcByPage(Integer pageNum,Integer pageSize,String productName,Integer categoryId) ;
   
    ServerResponse<MmallProductBo> detail(Integer productId) ;
  
    ServerResponse<String> updateSelStatus(Integer productId,Integer status) ;
    
    ServerResponse<String> saveProduct(Integer categoryId,String name,String subtitle,String mainImage,String subImages,
			String detail,Double price,Integer stock,Integer status,Integer id) ;
    
    ServerResponse<PageInfo<MmallProduct>> listByOrderPage(String productName,Integer categoryId,
	Integer pageNum,Integer pageSize,String orderBy);

}
