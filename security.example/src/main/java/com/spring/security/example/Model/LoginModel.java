package com.spring.security.example.Model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class LoginModel
{
	@Email
	@NotEmpty
	private String email;
	@NotBlank
	private String password;
	
	
	
	public String getEmail()
	{
		return email;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	
	
}
