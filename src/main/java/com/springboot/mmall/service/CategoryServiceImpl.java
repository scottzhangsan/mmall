package com.springboot.mmall.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.dao.MmallCategoryMapper;
import com.springboot.mmall.pojo.MmallCategory;

@Service
public class CategoryServiceImpl implements ICategoryService {
	
	private static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class) ;
	
	@Autowired
	private MmallCategoryMapper categoryMapper ;
    
	@Transactional
	public ServerResponse<String> addCategory(String categoryName, Integer parentId) {
		MmallCategory record = new MmallCategory() ;
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		record.setName(categoryName);
		record.setParentId(parentId);
		categoryMapper.insertSelective(record);
		return ServerResponse.createBySuccessMessage("新增品类成功");
	}
    @Transactional
	public ServerResponse<String> updateCategory(String categoryName, Integer categoryId) {
		MmallCategory record = new MmallCategory() ;
		record.setName(categoryName);
		record.setId(categoryId);
	    int result = categoryMapper.updateByPrimaryKey(record);
	    if (result != 1) {
			return ServerResponse.createByErrorMessage("更新品类名称失败") ;
		}
	    
	    return ServerResponse.createBySuccessMessage("修改品类名称失败") ;
	}
    /**
     * 查询平级的子节点
     */
	public ServerResponse<List<MmallCategory>> listParallerChildren(Integer parentId) {
		List<MmallCategory> list = categoryMapper.listByParentId(parentId) ;
		
		if (CollectionUtils.isEmpty(list)) {
			logger.info("该节点没有相关的子节点",list);
		}
		
		return ServerResponse.createBySuccess(list);
	}
	/**
	 *递归查询父节点下的所有的子节点
	 */
	@Override
	public ServerResponse<List<Integer>> listDeepChildren(Integer parentId) {
		Set<MmallCategory> set = new HashSet<>() ;
		
		set = resursionQuery(parentId, set) ;
		
		List<Integer> list = Lists.newArrayList() ;
		/**
		 * java8流转换的应用
		 */
		if (CollectionUtils.isNotEmpty(set)) {
			list = set.stream().mapToInt((e) ->e.getId()).boxed().collect(Collectors.toList()) ;
		}
		
		return ServerResponse.createBySuccess(list);
	}
	
	/**
	 * 递归查询
	 * @param parentId
	 * @param set
	 * @return
	 */
	private Set<MmallCategory> resursionQuery(Integer parentId,Set<MmallCategory> set){
		//查询当前节点
		MmallCategory category = categoryMapper.selectByPrimaryKey(parentId) ;
		//如果当前的节点为空直接返回空的集合
		if (category == null ) {
			return set ;
		}
		set.add(category) ;
		//查询当前节点的子节点
		List<MmallCategory> categories = categoryMapper.listByParentId(category.getId());
		//递归查询所有的子节点 ，当节点为空的时候结束递归查询
		for (MmallCategory mmallCategory : categories) {
			resursionQuery(mmallCategory.getId(), set) ;
		}
		
		return set ;
	}
	
	
	
	

}
