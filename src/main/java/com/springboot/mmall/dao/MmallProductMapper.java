package com.springboot.mmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.springboot.mmall.pojo.MmallProduct;

public interface MmallProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MmallProduct record);

    int insertSelective(MmallProduct record);

    MmallProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MmallProduct record);

    int updateByPrimaryKey(MmallProduct record);
    
    List<MmallProduct> listProduct() ;
    
    
    List<MmallProduct> serachProduct(@Param("productName")String productName,@Param("categoryIds")List<Integer> categoryIds) ;
}