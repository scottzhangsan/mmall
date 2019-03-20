package com.springboot.mmall.dao;

import com.springboot.mmall.pojo.MmallCart;

public interface MmallCartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MmallCart record);

    int insertSelective(MmallCart record);

    MmallCart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MmallCart record);

    int updateByPrimaryKey(MmallCart record);
}