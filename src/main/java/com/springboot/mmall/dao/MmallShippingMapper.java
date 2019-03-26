package com.springboot.mmall.dao;

import org.apache.ibatis.annotations.Param;

import com.springboot.mmall.pojo.MmallShipping;

public interface MmallShippingMapper {
    int deleteByPrimaryKey(@Param("id")Integer id,@Param("userId")Integer userId);

    int insert(MmallShipping record);

    int insertSelective(MmallShipping record);

    MmallShipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MmallShipping record);

    int updateByPrimaryKey(MmallShipping record);
}