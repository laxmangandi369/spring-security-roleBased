package com.spring.security.example.Model;

import java.util.Date;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SignupModel
{
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	@NotEmpty
	private String email;
	@NotEmpty

	private String password;

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getDateOfBirth()
	{
		return dateOfBirth;
	}

	public String getEmail()
	{
		return email;
	}

	public String getPassword()
	{
		return password;
	}

}
