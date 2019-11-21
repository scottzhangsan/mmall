package com.springboot.mmall.service;

import com.github.pagehelper.PageInfo;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.pojo.MmallUser;
import com.springboot.mmall.vo.MmallUserVo;

public interface IUserService {

	
	ServerResponse<MmallUser> login(String username,String password);
	
	ServerResponse<PageInfo<MmallUser>> listMmallUser(int pageNum,int pageSize) ;
	
	ServerResponse<String> register(MmallUserVo userVo) ;
	
	ServerResponse<String> getUserQuestion(String username) ;
	
	ServerResponse<String> checkAnswer(String username,String question,String answer) ;
	
	ServerResponse<String> resertPassword(String username,String newPassword,String token);
	
	ServerResponse<String> updateUserInfo(MmallUserVo vo,MmallUser user) ;
}
