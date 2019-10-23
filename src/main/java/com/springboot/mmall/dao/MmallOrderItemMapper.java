package com.springboot.mmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.springboot.mmall.pojo.MmallOrderItem;
import com.springboot.mmall.vo.OrderItemVo;

public interface MmallOrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MmallOrderItem record);

    int insertSelective(MmallOrderItem record);

    MmallOrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MmallOrderItem record);

    int updateByPrimaryKey(MmallOrderItem record);
    //通过订单号和用户ID查询订单信息
    List<MmallOrderItem> listOrderItemByOrderNoAndUserId(@Param("orderNo")Long orderNo,@Param("userId")Integer userId) ;
    //批量插入订单详情
    int batchInsertOrderItem(List<MmallOrderItem> items) ;
    
    List<OrderItemVo> listByOrderNo(Long orderNo) ;

}