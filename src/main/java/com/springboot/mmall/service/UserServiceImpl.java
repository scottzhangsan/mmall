package com.springboot.mmall.service;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.mmall.common.Const;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.common.TokenCache;
import com.springboot.mmall.dao.MmallUserMapper;
import com.springboot.mmall.pojo.MmallUser;
import com.springboot.mmall.util.BeanMapperUtil;
import com.springboot.mmall.util.MD5Util;
import com.springboot.mmall.vo.MmallUserVo;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private MmallUserMapper mmallUserMapper ;

	public ServerResponse<MmallUser> login(String username, String password) {
		//加密过后的密码
		String newPassword = MD5Util.getMD5(password);
		int count = mmallUserMapper.countUserByUsernameAndPassword(username, newPassword);
		if(count != 1){
			return ServerResponse.createByErrorMessage("用户名或密码错误") ;
		}
		
		return ServerResponse.createBySuccess(mmallUserMapper.selectMmallUserByNameAndPassword(username, newPassword));
	}

	public ServerResponse<PageInfo<MmallUser>> listMmallUser(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<MmallUser> Info = new PageInfo<MmallUser>(mmallUserMapper.listMmallUser()) ;
		return ServerResponse.createBySuccess(Info) ;
	}
    @Transactional
	public ServerResponse<String> register(MmallUserVo userVo) {
		
		int count = mmallUserMapper.countUserByUsernameAndPassword(userVo.getUsername(), null) ;
		if(count > 0){
			return ServerResponse.createByErrorMessage("注册失败用户名已经存在") ;
		}
		
		MmallUser user = BeanMapperUtil.map(userVo, MmallUser.class) ;
		user.setRole(Const.Role.ROLE_CUSTOMER);
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		//MD5加密
		user.setPassword(MD5Util.getMD5(user.getPassword()));
		int result = mmallUserMapper.insertSelective(user) ;
		if (result != 1) {
			return ServerResponse.createByErrorMessage("注册失败") ;
		}
		
		return ServerResponse.createBySuccess("注册成功");
	}

	public ServerResponse<String> getUserQuestion(String username) {
		MmallUser user = mmallUserMapper.selectMmallUserByNameAndPassword(username, null) ;
		if (user != null && StringUtils.isNotEmpty(user.getQuestion())) {
			return ServerResponse.createBySuccessMessage(user.getQuestion()) ;
		}
		return ServerResponse.createByErrorMessage("该用户未设置找回密码问题");
	}

	public ServerResponse<String> checkAnswer(String username, String question, String answer) {
		int result = mmallUserMapper.countCheckAnswer(username, question, answer) ;
		if (result != 1) {
			return ServerResponse.createByErrorMessage("问题答案错误");
		}
		String uuid = UUID.randomUUID().toString() ;
		TokenCache.setKey("token_"+username, uuid);
		return ServerResponse.createBySuccess(uuid);
	}
   @Transactional
	public ServerResponse<String> resertPassword(String username, String newPassword, String token) {
		String localToken = TokenCache.getValue("token_"+username) ;
		//如果token为空，或者传递过来的token和缓存中不相同，返回token失效
		if (localToken == null || !token.equals(localToken)) {
			return ServerResponse.createByErrorMessage("token已经失效");
		}
		int result = mmallUserMapper.updatePasswordByUsername(username, newPassword) ;
		if (result != 1) {
			return ServerResponse.createByErrorMessage("修改密码失效") ;
		}
		return ServerResponse.createBySuccessMessage("修改密码成功");
	}

	public ServerResponse<String> updateUserInfo(MmallUserVo vo,MmallUser user) {
		user = assemUserInfo(user, vo);
		int result = mmallUserMapper.updateByPrimaryKey(user) ;
		if (result != 1) {
			return ServerResponse.createByErrorMessage("更改用户信息失败");
		}
		return ServerResponse.createBySuccessMessage("更新用户信息成功");
	}
	
	private MmallUser assemUserInfo(MmallUser user,MmallUserVo vo){
		user.setEmail(vo.getEmail());
		user.setPhone(vo.getPhone());
		user.setQuestion(vo.getQuestion());
		user.setAnswer(vo.getAnswer());
		return user ;
	}
	
	

}
