package com.spring.security.example.ExceptionHandling;

public class CustomizedException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private final int errorCode;

	public CustomizedException(String message,Throwable t)
	{
		super(message,t);
//		this.errorCode = errorCode;
	}
	
	public CustomizedException(String message)
	{
		super(message);
//		this.errorCode = errorCode;
	}

//	public int getErrorCode()
//	{
//		return errorCode;
//	}
}
