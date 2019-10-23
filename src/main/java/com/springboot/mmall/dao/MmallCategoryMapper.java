package com.springboot.mmall.dao;

import java.util.List;

import com.springboot.mmall.pojo.MmallCategory;

public interface MmallCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MmallCategory record);

    int insertSelective(MmallCategory record);

    MmallCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MmallCategory record);

    int updateByPrimaryKey(MmallCategory record);
    
    List<MmallCategory> listByParentId(Integer parentId) ; //通过parentId查询品类信息
}