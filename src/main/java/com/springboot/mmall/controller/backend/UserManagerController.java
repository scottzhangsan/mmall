package com.springboot.mmall.controller.backend;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.springboot.mmall.common.Const;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.pojo.MmallUser;
import com.springboot.mmall.service.IUserService;

/**
 * 后台用户管理功能
 * @author yzhang98
 *
 */
@Controller
@RequestMapping("/manage/user")
public class UserManagerController {
	
	@Autowired
	private IUserService userService ;
	
	@ResponseBody
	@RequestMapping("login")
	public ServerResponse<MmallUser> login(String username,String password,HttpSession session){
		ServerResponse<MmallUser> response = userService.login(username, password) ;
		if (response.isSuccess()) {
			MmallUser user = response.getData() ;
			if (user.getRole() == Const.Role.ROLE_ADMIN) {
				session.setAttribute(Const.CURRENT_USER, user);
				return response ;
			}else{
				return ServerResponse.createByErrorMessage("不是管理员无法的登录");
			}
		}
		return  response;
	}
	
	@ResponseBody
	@RequestMapping(value="/list",method = RequestMethod.POST)
	public ServerResponse<PageInfo<MmallUser>> listUser(@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue="10")Integer pageSize) {
		return userService.listMmallUser(pageNum, pageSize);
	}
	

}
