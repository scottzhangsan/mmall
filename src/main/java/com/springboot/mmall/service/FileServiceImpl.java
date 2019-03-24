package com.springboot.mmall.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.springboot.mmall.util.FTPUtil;
@Service
public class FileServiceImpl implements IFileService {
	
	@Value("${upload.filePath}")
	private String uploadFilePAth ;

	@Override
	public String uploadFile(MultipartFile file, HttpServletRequest request) {
		File file2 = new File(uploadFilePAth) ;
		if (!file2.exists()) {
			file2.mkdirs() ;
		}
		String fileName = file.getOriginalFilename() ;
		//获取扩展名
		String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1) ;
		//唯一的文件名
		String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName ;
		
		File targetFile = new File(uploadFilePAth, uploadFileName) ;
		
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//将targetFile上传到文件服务器
		List<File> files = Lists.newArrayList(targetFile) ;
		try {
			FTPUtil.uploadFile(files);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		targetFile.delete() ;
		return targetFile.getName();
	}


}
