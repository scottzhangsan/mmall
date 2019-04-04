package com.springboot.mmall;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.mmall.learn.Person;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootApplicationTest {
	@Autowired
	private Person person ;
	@Test
	public void test01(){
		System.out.println(person.getLastName()+":"+person.getAge()+":"+person.getLists()+":"+person.getMaps());
		
	}

}
