package com.springboot.mmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.springboot.mmall.pojo.MmallCart;

public interface MmallCartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MmallCart record);

    int insertSelective(MmallCart record);

    MmallCart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MmallCart record);

    int updateByPrimaryKey(MmallCart record);
    //通过人员和商品ID查询购物车
    MmallCart selectCartByUserAndProduct(@Param("userId")Integer userId,@Param("productId")Integer productId) ;
    //根据当前用户查询当前用户的购物车信息
    List<MmallCart> listCartByUser(Integer userId) ;
    
    int deletProductByProductIdAndUser(@Param("userId")Integer userId,@Param("productIds")List<String> productIds) ;
    //根据用户名查询选中的购物车信息
    List<MmallCart> listByUserIdSelected(@Param("userId")Integer userId) ;

}