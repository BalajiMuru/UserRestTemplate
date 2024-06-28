package com.springboot.user.service.exception;

public class ResourseNotFoundException extends RuntimeException{
	
	public ResourseNotFoundException() {
		super("Resource not found on server");
	}
	

	public ResourseNotFoundException(String message)
	{
		super(message);
	}
}
