package com.springboot.mmall.controller.backend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 商品后台管理
 * @author yzhang98
 *
 */
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.springboot.mmall.bo.MmallProductBo;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.pojo.MmallProduct;
import com.springboot.mmall.service.IFileService;
import com.springboot.mmall.service.IProductService;
import com.springboot.mmall.util.PropertiesUtil;
@RestController
@RequestMapping("/manage/product")
public class ProductManageController {	
	
	@Autowired
	private IProductService productService ;
	@Autowired
	private IFileService fileServie ;
	
	@RequestMapping("/list")
	public ServerResponse<PageInfo<MmallProduct>> listProductByPage(
			                                 @RequestParam(value="pageNum",defaultValue="1")Integer pageNum,
			                                 @RequestParam(value="pageSize",defaultValue="10") Integer pageSize){
	return productService.listByPage(pageNum, pageSize) ;
	}
	
	@RequestMapping("/search")
	public ServerResponse<PageInfo<MmallProduct>> searchProductByPage(
			                                @RequestParam(value="pageNum",defaultValue="1")Integer pageNum,
                                            @RequestParam(value="pageSize",defaultValue="10") Integer pageSize,
			                                String productName,Integer categoryId
			){
		
	  return productService.serarcByPage(pageNum, pageSize, productName, categoryId);	
	}
   
	/**
	 * 文件上传
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping("upload")
	public ServerResponse<Map<String, String>> upload(MultipartFile file,HttpServletRequest request){
     	String fileName =	fileServie.uploadFile(file, request);
	   Map<String, String> map = new HashMap<>() ;
	   map.put("path", PropertiesUtil.getValue("ftp.server.http.prefix")+fileName);
	   map.put("uri", fileName) ;
     	return ServerResponse.createBySuccess(map) ;
		
	}
	/**
	 * 商品详情
	 * @param productId
	 * @return
	 */
	@RequestMapping("detail")
	public ServerResponse<MmallProductBo> detail(Integer productId){
		return productService.detail(productId) ;
	}
	/**
	 * 商品上下架
	 * @param productId
	 * @param status
	 * @return
	 */
	@RequestMapping("sel_status")
	public ServerResponse<String> updateSelStatus(Integer productId,Integer status){
		return productService.updateSelStatus(productId, status) ;
	}
	/**
	 * 新增或者更新商品
	 * @param categoryId
	 * @param name
	 * @param subtitle
	 * @param mainImage
	 * @param subImages
	 * @param detail
	 * @param price
	 * @param stock
	 * @param status
	 * @param id
	 * @return
	 */
	@RequestMapping("save")
	public ServerResponse<String> saveProduct(Integer categoryId,String name,String subtitle,String mainImage,String subImages,
			String detail,Double price,Integer stock,Integer status,Integer id){
		return productService.saveProduct(categoryId, name, subtitle, mainImage, subImages, detail, price, stock, status, id) ;
	}
	
	
	
	
}
