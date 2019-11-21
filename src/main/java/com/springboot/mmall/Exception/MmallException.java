package com.springboot.mmall.Exception;
/**
 * 统一的Exception
 * @author yzhang98
 *
 */
public class MmallException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MmallException() {
		super();
	}

	
	public MmallException(String message, Throwable cause) {
		super(message, cause);
	}

	public MmallException(String message) {
		super(message);
	}

	public MmallException(Throwable cause) {
		super(cause);
	}


	

}
