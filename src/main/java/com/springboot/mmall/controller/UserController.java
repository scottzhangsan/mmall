package com.springboot.mmall.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.pojo.MmallUser;
import com.springboot.mmall.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService ;
	@ResponseBody
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public ServerResponse<MmallUser> login(String username,String password,HttpSession session){
		
		return userService.login(username, password) ;
	}
	@ResponseBody
	@RequestMapping(value="/list",method = RequestMethod.POST)
	public ServerResponse<PageInfo<MmallUser>> listUser(@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue="10")Integer pageSize){
		return userService.listMmallUser(pageNum, pageSize);
	}

}
