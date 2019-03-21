package com.springboot.mmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.springboot.mmall.pojo.MmallUser;

public interface MmallUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MmallUser record);

    int insertSelective(MmallUser record);

    MmallUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MmallUser record);

    int updateByPrimaryKey(MmallUser record);
    //通过用户名和密码判断是否存在当前用户
    int countUserByUsernameAndPassword(@Param("username")String username,@Param("password")String password) ;
    //通过用户名和密码查询用户
    MmallUser selectMmallUserByNameAndPassword(@Param("username")String username,@Param("password")String password);
    //获取所有的用户信息
    List<MmallUser> listMmallUser() ;
    
    
    
}