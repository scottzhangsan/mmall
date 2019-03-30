package com.springboot.mmall.dao;

import org.apache.ibatis.annotations.Param;

import com.springboot.mmall.pojo.MmallOrder;

public interface MmallOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MmallOrder record);

    int insertSelective(MmallOrder record);

    MmallOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MmallOrder record);

    int updateByPrimaryKey(MmallOrder record);
    
    MmallOrder selectByOrderNoAndUserId(@Param("orderNo")Long orderNo,@Param("userId")Integer userId);
    //通过订单号查询订单
    MmallOrder selectByOrderNo(Long order) ;
}