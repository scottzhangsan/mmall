package com.springboot.mmall.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.mmall.common.ServerResponse;

/**
 * 项目统一的异常处理
 * @author yzhang98
 *
 */
@ControllerAdvice
public class MmallGlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ServerResponse<String> exceptionHandler(Exception e){
		return ServerResponse.createByErrorMessage("系统异常") ;
	}

}
