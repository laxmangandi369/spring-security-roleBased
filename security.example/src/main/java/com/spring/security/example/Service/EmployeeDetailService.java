package com.spring.security.example.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.spring.security.example.Entity.Employees;
import com.spring.security.example.Repository.EmployeeRepository;

@Service
public class EmployeeDetailService implements UserDetailsService
{
	@Autowired
	EmployeeRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		Employees employee = null;
		
		employee = repository.findByEmail(username);
		if(employee!=null)
		{
			return EmployeeDetailsImpl.build(employee);
		}
		else {
			throw new UsernameNotFoundException(username+" not found");
		}
	}
	
	
}
