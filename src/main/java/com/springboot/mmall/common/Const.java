package com.springboot.mmall.common;
/**
 * 
 * @author yzhang98
 *常量类
 */
public class Const {
	
	public static final String CURRENT_USER = "currentUser";
	
	public interface Role{  //运用接口起到分组的效果
		int ROLE_CUSTOMER = 0 ; //普通用户
		int ROLE_ADMIN = 1 ; //管理员
	}
	

}
