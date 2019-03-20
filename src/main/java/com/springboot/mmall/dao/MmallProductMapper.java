package com.springboot.mmall.dao;

import com.springboot.mmall.pojo.MmallProduct;

public interface MmallProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MmallProduct record);

    int insertSelective(MmallProduct record);

    MmallProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MmallProduct record);

    int updateByPrimaryKey(MmallProduct record);
}