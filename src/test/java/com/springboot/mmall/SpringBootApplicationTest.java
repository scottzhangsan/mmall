package com.springboot.mmall;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.mmall.pojo.MmallPayInfo;
import com.springboot.mmall.service.PayInfoServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootApplicationTest {
	@Autowired
	private PayInfoServiceImpl payInfoServiceImpl;

	@Test
	@Rollback(value = false)
	public void testTransation() {

		for (int i = 0; i < 1200; i++) {
			Thread t1 = new Thread(() -> add());
			t1.start();
		}

	}

	private void add() {
		MmallPayInfo record = new MmallPayInfo();
		record.setCreateTime(new Date());
		record.setOrderNo(1234567890L);
		record.setPayPlatform(1);
		record.setPlatformNumber("aa");
		record.setUpdateTime(new Date());
		record.setUserId(22);
		payInfoServiceImpl.add(record);
	}

}
