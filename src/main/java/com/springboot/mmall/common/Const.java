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
	
	public interface Cart{
		int isChecked = 1 ;
		int notChecked = 0 ;
		String LIMIT_NUM_SUCCESS ="LIMIT_NUM_SUCCESS" ;
		String LIMIT_NUM_FAIL ="LIMIT_NUM__FAIL" ;
	}
	

}
