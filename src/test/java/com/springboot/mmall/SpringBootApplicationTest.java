package com.springboot.mmall;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.mmall.service.EmailServiceImpl;
import com.springboot.mmall.service.IShippingService;
import com.springboot.mmall.service.ShippingManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootApplicationTest {
	@Autowired
	private EmailServiceImpl emailServiceImpl ;
	
	
	/*@Test
	public void test01(){
		emailServiceImpl.sendSimpleEmail("1154769609@qq.com", "test", "hello world");
	}*/
	/*@Test
	public void test02(){
		 String content="<html>\n" +
		            "<body>\n" +
		            "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
		            "</body>\n" +
		            "</html>";
		emailServiceImpl.sendHtmlEmail("1154769609@qq.com", "test", content);
	}*/
	
	/*@Test
	public void test03(){
		 String filePath="C:\\Users\\yzhang98\\Desktop\\New Text Document.txt";
		  emailServiceImpl.sendAttachmentsMail("1154769609@qq.com", "test", "测试发附件的邮件",filePath);
	}*/
	@Autowired
	private IShippingService shippingService ;
	@Autowired
	private ShippingManager shippingManager ;
	@Test
	@Rollback(value=false)
	public void test04() throws InterruptedException{
		Integer id = 30 ;
		
		ExecutorService service = Executors.newFixedThreadPool(4) ;
		
		for (int i = 0; i < 999; i++) {
			service.execute(()->shippingManager.updateShipping(id));
		}
      Thread.sleep(36000);
	}
	
	

}
