package com.springboot.mmall.common;
/**
 * 
 * @author yzhang98
 *
 */
public enum ResponseCode {
	
	SUCCESS(0,"SUCCESS"),
	ERROR(1,"ERROR"),
	NEED_LOGIN(2,"NEED_LOGIN") ;
	
	private int code ;
	private String desc ;
	
	 ResponseCode(int code,String desc) {
		this.code = code ;
		this.desc = desc ;
	}
	 
	 public int getCode(){
		 return this.code ;
	 }
	 
	 public String getDesc(){
		 return this.desc ;
	 }
	

}
