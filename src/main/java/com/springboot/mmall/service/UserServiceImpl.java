package com.springboot.mmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.dao.MmallUserMapper;
import com.springboot.mmall.pojo.MmallUser;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private MmallUserMapper mmallUserMapper ;

	public ServerResponse<MmallUser> login(String username, String password) {
		int count = mmallUserMapper.countUserByUsernameAndPassword(username, password);
		if(count != 1){
			return ServerResponse.createByErrorMessage("用户名或密码错误") ;
		}
		
		return ServerResponse.createBySuccess(mmallUserMapper.selectMmallUserByNameAndPassword(username, password));
	}

	public ServerResponse<PageInfo<MmallUser>> listMmallUser(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<MmallUser> Info = new PageInfo<MmallUser>(mmallUserMapper.listMmallUser()) ;
		return ServerResponse.createBySuccess(Info) ;
	}

}
