package com.springboot.mmall.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.springboot.mmall.pojo.MmallOrder;
import com.springboot.mmall.vo.OrderVo;
import com.springboot.mmall.vo.ProductOrderItemVo;

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
    
    ProductOrderItemVo getOrderProductInfo(Long orderNo) ;
    
    List<OrderVo> listOrder();
    
    int cancelOrderTimeOrder(@Param("overTime")Date overTime) ;
    
    int cancelOrderByOrderNo(@Param("orderNo")Long orderNo,@Param("status")Integer status) ;
    /**
     * 查询超时未付款的订单
     * @param overTime
     * @return
     */
    List<MmallOrder> listOverTimeOrder(@Param("overTime")Date overTime);
}