package com.springboot.mmall.service;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * 邮件服务
 * @author yzhang98
 *
 */
@Component
public class EmailServiceImpl {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass()) ;
	@Value("${mail.fromMail.addr}")
	private String mailFrom ;
	
	@Autowired
	private JavaMailSender mailSender ;
	/**
	 * 发送简单的邮件
	 * @param to
	 * @param subject
	 * @param content
	 */
	public void sendSimpleEmail(String to,String subject,String content){
		SimpleMailMessage message = new SimpleMailMessage() ;
		message.setFrom(mailFrom);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		try {
			mailSender.send(message);
			logger.info("发送邮件成功!");
		} catch (Exception e) {
			logger.error("发送邮件失败");
		}	
	}
	/**
	 * 发送带Html格式的邮件
	 * @param to
	 * @param subject
	 * @param content
	 */
	public void sendHtmlEmail(String to,String subject,String content){
		MimeMessage mailMessage = mailSender.createMimeMessage() ;
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true) ;
			messageHelper.setFrom(mailFrom);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(content,true);
		    mailSender.send(mailMessage);
		    logger.info("发送带HTML的邮件成功");
		} catch (Exception e) {
			logger.error("发送带Html的邮件失败",e);
		}
	}
	/**
	 * 发送带附件的邮件
	 * @param to
	 * @param subject
	 * @param content
	 * @param filePath
	 */
	public void sendAttachmentsMail(String to,String subject,String content,String filePath){
		MimeMessage mailMessage = mailSender.createMimeMessage() ;
		
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true);
			messageHelper.setFrom(mailFrom);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(content);
			FileSystemResource resource = new FileSystemResource(new File(filePath));
			String fileName = "test";
			messageHelper.addAttachment(fileName, resource);
		    mailSender.send(mailMessage);
		    logger.info("发送带附件的邮件成功");
		} catch (Exception e) {
			logger.error("发送带附件的邮件失败",e);
		}
	}
	

}
