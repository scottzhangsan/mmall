package com.springboot.mmall.common;

public enum OrderEnum {
	
	CANCEL(0,"已取消"),NOT_PAY(10,"未付款"),
	ALREADY_PAY(20,"已付款"),ALREADY_DELIVER(40,"已发货"),
	FINISH(50,"已完成"),CLOSED(60,"交易关闭")
	;
	
	int code ;
	
	String msg ;

	
	private OrderEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}


	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
	
	

 public static void main(String[] args) {
	System.out.println(OrderEnum.ALREADY_PAY.getCode());
}
	
	
	
	

}
