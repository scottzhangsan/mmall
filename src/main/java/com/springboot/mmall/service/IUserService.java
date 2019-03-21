package com.springboot.mmall.service;

import com.github.pagehelper.PageInfo;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.pojo.MmallUser;

public interface IUserService {

	
	ServerResponse<MmallUser> login(String username,String password);
	
	ServerResponse<PageInfo<MmallUser>> listMmallUser(int pageNum,int pageSize) ;
}
