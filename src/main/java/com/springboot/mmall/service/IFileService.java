package com.springboot.mmall.service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;


/**
 * 文件上传
 * @author yzhang98
 *
 */
public interface IFileService {
	
	String uploadFile(MultipartFile file,HttpServletRequest request) ;

}
