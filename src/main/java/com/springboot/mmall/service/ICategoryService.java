package com.springboot.mmall.service;



import java.util.List;
import java.util.Set;

import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.pojo.MmallCategory;

public interface ICategoryService {
	
	ServerResponse<String> addCategory(String categoryName,Integer parentId) ;
	
	ServerResponse<String> updateCategory(String categoryName,Integer categoryId) ;
	//查询平级的子节点
	ServerResponse<List<MmallCategory>> listParallerChildren(Integer parentId) ;
	//递归查询节点下的所有的子节点
	ServerResponse<List<Integer>> listDeepChildren(Integer parentId) ;

}
