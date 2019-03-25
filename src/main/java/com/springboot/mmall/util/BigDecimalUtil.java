package com.springboot.mmall.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 金额计算工具类
 * @author yzhang98
 *
 */
public class BigDecimalUtil {
	/**
	 * 私有构造方法
	 */
	private BigDecimalUtil(){
		
	}
	/*
	 * add 方法
	 */
	public static BigDecimal add(BigDecimal b1,BigDecimal b2){
		if (b1 == null && b2 != null ) {
			return new BigDecimal("0").add(b2) ;
		}
		if (b1 != null && b2 == null ) {
			return b1.add(new BigDecimal("0")) ;
		}
		
		if (b1 == null && b2 == null ) {
			return new BigDecimal("0") ;
		}
		
		return b1.add(b2) ;
	}
	
	/**
	 * 相减的方法
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static BigDecimal sub(BigDecimal b1,BigDecimal b2){
		if (b1 == null && b2 != null ) {
			return new BigDecimal("0").subtract(b2) ;
		}
		if (b1 != null && b2 == null ) {
			return b1.subtract(new BigDecimal("0")) ;
		}
		
		if (b1 == null && b2 == null ) {
			return new BigDecimal("0") ;
		}
		
		return b1.subtract(b2) ;
	}
	
	/**
	 * 相乘的方法
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal b1,BigDecimal b2){
		if (b1 == null || b2 == null ) {
			return new BigDecimal("0") ;
		}
		
		return b1.multiply(b2) ;
	}
	
	/**
	 * 相除的方法
	 * @param b1
	 * @param b2
	 * sale保留几位小数
	 * @return
	 */
	public static BigDecimal divide(BigDecimal b1,BigDecimal b2,int sale){
		if (b1 == null || b2 == null ) {
			throw new NullPointerException() ;
		}
		
		return b1.divide(b2,2,RoundingMode.HALF_UP) ;
	}
	
	
	public static void main(String[] args) {
		System.out.println(BigDecimalUtil.divide(new BigDecimal("10"), new BigDecimal("3"), 2));
	}
	
	
	
	
	
	
	

}
