package com.springboot.mmall.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL) //为空的值不进行返回
public class ServerResponse<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int status ;
	private String msg ;
	private T data ;
  
	/**
   * 私有构造方法
   * @param status
   */
	private ServerResponse(int status){
		this.status = status ;
	}
	
	private ServerResponse(int status,T data){
		this.status = status ;
		this.data = data ;
	}
	
	private ServerResponse(int status,String msg,T data){
		this.status = status ;
		this.msg = msg ;
		this.data = data ;
	}
	
	private ServerResponse(int status,String msg){
		this.status = status ;
		this.msg = msg ;
	}
	//加上注解防止返回json对象被序列化
	@JsonIgnore
	public boolean isSuccess(){
		return this.status == ResponseCode.SUCCESS.getCode() ;
	}
	
	public int getStatus(){
		return this.status ;
	}
	
	public String getMsg(){
		return this.msg ;
	}
	public T getData(){
		return this.data ;
	}
	
	public static <T>ServerResponse<T> createBySuccess(){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode()) ;
	}
	
	public static <T>ServerResponse<T> createBySuccessMessage(String msg){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg) ;
	}
	/**
	 * 能解决T也是String类型的构造方法的创建问题
	 * @param data
	 * @return
	 */
	public static <T>ServerResponse<T> createBySuccess(T data){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data) ;
	}
	
	public static <T>ServerResponse<T> createBySuccess(String msg,T data){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data) ;
	}
	/**
	 * 公共的错误
	 * @return
	 */
   public static <T>ServerResponse<T> createByError(){
	   return new ServerResponse<T>(ResponseCode.ERROR.getCode()) ;
   }
   
   public static <T>ServerResponse<T> createByErrorMessage(String errorMsg){
	   return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMsg) ;
   }
   public static <T>ServerResponse<T> createByErrorCodeMessage(int errorCode,String errorMsg){
	   return new ServerResponse<T>(errorCode,errorMsg) ;
   }
}
