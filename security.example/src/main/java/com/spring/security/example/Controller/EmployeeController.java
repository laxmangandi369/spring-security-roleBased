package com.spring.security.example.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController extends BaseController
{
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@GetMapping("/all")
	public String checkAllAccess()
	{
		return "All Access Area";
	}
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public String getUserAccess()
	{
		return "authenticated user Access Area";
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String getAdminAccess()
	{
		return "authenticated admin access Area";
	}
	
	
}
