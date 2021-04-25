package com.github.tutorial.Error;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistException extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	

}
