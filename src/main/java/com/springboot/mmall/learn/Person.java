package com.springboot.mmall.learn;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * 学习yml的语法 ，将配置文件的值放到Person的属性中
 * @author yzhang98
 *必须是Spring容器的组件
 */
@ConfigurationProperties(prefix="person")   //告诉Spring将本类中所有属性和配置文件中的相关的配置进行绑定,配置文件中哪个下面的属性进行一一的映射
@Component
public class Person {
	
	private String lastName ;
	private int        age ;
	private Map<String, String> maps;
	private List<String> lists ;
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Map<String, String> getMaps() {
		return maps;
	}
	public void setMaps(Map<String, String> maps) {
		this.maps = maps;
	}
	public List<String> getLists() {
		return lists;
	}
	public void setLists(List<String> lists) {
		this.lists = lists;
	}
	
	
	
	public static void main(String[] args) {
		
	}
	

}
