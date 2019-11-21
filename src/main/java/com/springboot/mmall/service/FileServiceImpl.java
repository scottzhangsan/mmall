package com.springboot.mmall.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.springboot.mmall.util.FTPUtil;
@Service
public class FileServiceImpl implements IFileService {
	
	private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class) ;
	
	@Value("${upload.filePath}")
	private String uploadFilePAth ;

	@Override
	public String uploadFile(MultipartFile file, HttpServletRequest request) {
		File file2 = new File(uploadFilePAth) ;
		if (!file2.exists()) {
			file2.mkdirs() ;
		}
		String fileName = file.getOriginalFilename() ;
		logger.info("上传的文件名为，{}",fileName);
		//获取扩展名
		String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1) ;
		//唯一的文件名
		String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName ;
		//在此文件加下构建文件 uuid.name 。
		File targetFile = new File(uploadFilePAth, uploadFileName) ;
		
		try {
			//把文件放到此文件下
			file.transferTo(targetFile);
		} catch (Exception e) {
			logger.error("上传文件失败", e); 
			return null ;
		}
		
		//将targetFile上传到文件服务器
		List<File> files = Lists.newArrayList(targetFile) ;
		try {
			FTPUtil.uploadFile(files);
		} catch (IOException e) {
		logger.error("上传到vsftpd服务失败",e);	
		}finally {
			//删除原来的文件
			targetFile.delete() ;
		}
		
		return targetFile.getName();
	}


}
