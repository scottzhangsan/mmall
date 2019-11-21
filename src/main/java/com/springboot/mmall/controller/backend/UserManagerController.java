package com.springboot.mmall.controller.backend;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.springboot.mmall.util.CookieUtil;
import com.springboot.mmall.util.JsonUtil;
import com.springboot.mmall.util.StringRedisUtil;

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
	@Autowired
	private StringRedisUtil redisUtil ;
	
	@ResponseBody
	@RequestMapping("login")
	public ServerResponse<MmallUser> login(String username,String password,HttpSession session,HttpServletResponse servletResponse,HttpServletRequest request){
		ServerResponse<MmallUser> response = userService.login(username, password) ;
		if (response.isSuccess()) {
			MmallUser user = response.getData() ;
			if (user.getRole() == Const.Role.ROLE_ADMIN) {
				session.setAttribute(Const.CURRENT_USER, user);
				//传入cookie的时候，   值为session的ID//用URLEncoder.encode编码一下，防止报错
				CookieUtil.addCookie(servletResponse, Const.MMALL_COOKIE_NAME,session.getId(), Const.COOKIE_EXPIRE_TIEM);
				//然后把用户信息放到redis里面 key sessionId value为用户信息
				redisUtil.put(session.getId(), user) ;
				//获取cookie值得时候也需要解码
				//MmallUser mmallUser = JsonUtil.json2Object(URLDecoder.decode(result), MmallUser.class) ;
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
