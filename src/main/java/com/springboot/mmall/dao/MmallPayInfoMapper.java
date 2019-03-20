package com.springboot.mmall.dao;

import com.springboot.mmall.pojo.MmallPayInfo;

public interface MmallPayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MmallPayInfo record);

    int insertSelective(MmallPayInfo record);

    MmallPayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MmallPayInfo record);

    int updateByPrimaryKey(MmallPayInfo record);
}