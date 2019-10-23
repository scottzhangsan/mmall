package com.springboot.mmall.controller;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.ReferenceType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageInfo;
import com.springboot.mmall.common.Const;
import com.springboot.mmall.common.ServerResponse;
import com.springboot.mmall.pojo.MmallUser;
import com.springboot.mmall.service.IUserService;
import com.springboot.mmall.util.CookieUtil;
import com.springboot.mmall.util.JsonUtil;
import com.springboot.mmall.util.StringRedisUtil;
import com.springboot.mmall.vo.MmallUserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService ;
	@Autowired
	private StringRedisUtil redisUtil;
	@ResponseBody
	@RequestMapping(value="/login")
	public ServerResponse<MmallUser> login(String username,String password,HttpSession session,HttpServletResponse servletResponse,HttpServletRequest request){
		ServerResponse<MmallUser> response = userService.login(username, password) ;
		//如果登录成功才设置Session
		if (response.isSuccess()) {
			//session.setAttribute(Const.CURRENT_USER, response.getData());
			redisUtil.putKeyWithTime(session.getId(), response.getData(),Const.SESSIONID_4_REDIS_EXPIRE_TIME) ;
			CookieUtil.addCookie(servletResponse, Const.MMALL_COOKIE_NAME, session.getId(), Const.COOKIE_EXPIRE_TIEM);
		}		
		return  response;
	}
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> logout(HttpSession session){
		session.removeAttribute(Const.CURRENT_USER);
		return ServerResponse.createBySuccess() ;
	}
	
	@ResponseBody
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ServerResponse<String> register(MmallUserVo userVo){
		return userService.register(userVo) ;
	}
	/**
	 * 获取用户信息
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="get_user_info")
	public ServerResponse<MmallUser> getMmuserInfo(HttpSession session,HttpServletRequest request){
		String sessionId = CookieUtil.getUid(request, Const.MMALL_COOKIE_NAME) ;
		MmallUser user = JsonUtil.json2Object(redisUtil.getValue(sessionId),MmallUser.class) ;
		
		//MmallUser user = (MmallUser)session.getAttribute(Const.CURRENT_USER) ;
		if (user == null) {
			return ServerResponse.createByErrorMessage("获取用户信息失败，用户未登陆") ;
		}
		
		return ServerResponse.createBySuccess(user) ;
		
	}
	
	/**
	 * 忘记密码
	 * @param username
	 * @return
	 */
	@RequestMapping("forget_get_question")
	@ResponseBody
	public ServerResponse<String> forgetQuestion(@RequestParam("username")String username){
		return userService.getUserQuestion(username) ;
	}
	
	/**检查问题答案
	 * 
	 * @param username
	 * @param question
	 * @param answer
	 * @return
	 */
	@RequestMapping("forget_check_answer")
	@ResponseBody
	public ServerResponse<String> forgetCheckAnswer(@RequestParam("username")String username,@RequestParam("question")String question,@RequestParam("answer")String answer){
		return userService.checkAnswer(username, question, answer);
	}
	
	/**
	 * 重置密码
	 * @return
	 */
	@RequestMapping("forget_resert_password")
	@ResponseBody
	public ServerResponse<String> forgetResertPassword(String username,String newPassword,String token){
		return userService.resertPassword(username, newPassword, token) ;
	}
	/**
	 * 登录状态更新个人信息
	 * @param vo
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update_info")
	public ServerResponse<String> updateUserInfo(MmallUserVo vo,HttpSession session){
		MmallUser user = (MmallUser) session.getAttribute(Const.CURRENT_USER) ;
		if (user == null ) {
			return ServerResponse.createByErrorMessage("用户未登录") ;
		}
		
		return userService.updateUserInfo(vo, user) ;
	}
	

}
