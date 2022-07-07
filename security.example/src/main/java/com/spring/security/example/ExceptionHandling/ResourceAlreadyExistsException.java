package com.spring.security.example.ExceptionHandling;

public class ResourceAlreadyExistsException extends RuntimeException
{
	public ResourceAlreadyExistsException(String message)
	{
		super(message);
	}
}
