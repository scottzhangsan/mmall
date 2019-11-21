package com.springboot.mmall.common;
/**
 * 
 * @author yzhang98
 *常量类
 */
public class Const {
	
	public static final String CURRENT_USER = "currentUser";
	
	public static final String MMALL_COOKIE_NAME = "MMALLCOOKIE" ;
	
	public static final int COOKIE_EXPIRE_TIEM = 60*30  ;// Cookie存活时间30分钟
	
	public static final  long SESSIONID_4_REDIS_EXPIRE_TIME = 60*30;  //redis保存sessionId时间
	
	public static final long LIMIT_OVER_TIEM_ORDER = 1000*60*30 ;  //超过30分钟未付款的订单进行取消
	
	public static final String MMALL_REDISSON_LOCK_NAME = "MMALL_REDISSON_LOCK" ;
	
	
	public interface Role{  //运用接口起到分组的效果
		int ROLE_CUSTOMER = 0 ; //普通用户
		int ROLE_ADMIN = 1 ; //管理员
	}	
	
	public interface Cart{
		int isChecked = 1 ;
		int notChecked = 0 ;
		String LIMIT_NUM_SUCCESS ="LIMIT_NUM_SUCCESS" ;
		String LIMIT_NUM_FAIL ="LIMIT_NUM__FAIL" ;
	}
	
	public interface AlipayNotify{
		
		String NOTIFY_SUCCESS = "success" ;
		String NOTIFY_FAILURE = "fail" ;
		
		String TRADE_SUCCESS = "TRADE_SUCCESS" ;
	}
	
	public enum PayPlatform{
		ALIPAY(1,"支付宝");
		int code ;
		String value ;
		
		private PayPlatform(int code, String value) {
			this.code = code;
			this.value = value;
		}

		public int getCode() {
			return code;
		}

		public String getValue() {
			return value;
		}	
		
	}
	
	public enum ProductStatus{
		ON_SALE(1,"在售") , SOLD_OUT(2,"下架");
		int code ;
		String value ;
		
		private ProductStatus(int code, String value) {
			this.code = code;
			this.value = value;
		}

		public int getCode() {
			return code;
		}

		public String getValue() {
			return value;
		}	
		
	}
	

}
