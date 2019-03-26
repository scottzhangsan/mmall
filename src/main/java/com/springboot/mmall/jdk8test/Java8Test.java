package com.springboot.mmall.jdk8test;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * java8Stream流相关的操作
 * @author yzhang98
 *
 */
public class Java8Test {
	
	/**
	 * stream() -为集合创建串行流
	 * parallelStream()为集合创建并行流
	 *  
	 * Predicate 断言
	 */
	//@Test
	public void test01(){
		List<String> strings = Arrays.asList("abc","","bc","efg","","abcd","","jkl") ;
		List<String> filtered = strings.stream().filter(x->StringUtils.isNoneBlank(x)).collect(Collectors.toList());
	     filtered.forEach(System.out::println);
	}
	/*
	 * map的应用
	 *function接口 ，传入 t ,返回 r .
	 */
	//@Test
	public void test02(){
		List<Integer> list = Arrays.asList(3,2,2,3,7,3,5) ;
		List<Integer> squaresList = list.stream().map(x->x*x).distinct().collect(Collectors.toList()) ;
		squaresList.forEach(System.out::println);
	}
	/**
	 * filter的应用，Predicate接口，传入值，返回true or false ;
	 * 通过设置的条件过滤出元素
	 */
	//@Test
	public void test03(){
		List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
		long count = strings.stream().filter(x->StringUtils.isEmpty(x)).count() ;
		System.out.println(count);
	}
	/**
	 * Collectors 类实现了很多归约操作，例如将流转换成集合和聚合元素。
	 * Collectors 可用于返回列表或字符串
	 */
	//@Test
	public void test04(){
		List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
		//List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
		//System.out.println("筛选列表: " + filtered);
		String string =strings.stream().filter(s->StringUtils.isNotBlank(s)).collect(Collectors.joining("-"));
		
		System.out.println(string);
		
	}
	/**
	 * summaryStatistics主要用于数字的统计
	 */
	@Test
	public void test05(){
		List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
		IntSummaryStatistics statistics = numbers.stream().mapToInt(x->x*9).summaryStatistics() ;
		System.out.println("获取平均值："+statistics.getAverage());
		System.out.println("获取数量："+statistics.getCount());
		System.out.println("获取最大值："+statistics.getMax());
		System.out.println("获取最小值："+statistics.getMin());
		System.out.println("取和："+statistics.getSum());
	}
}
