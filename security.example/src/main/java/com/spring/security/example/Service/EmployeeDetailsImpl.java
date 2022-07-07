package com.spring.security.example.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.security.example.Entity.Employees;


public class EmployeeDetailsImpl implements UserDetails
{

	private Long id;
	
	private String email;
	
	@JsonIgnore
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
		
	public EmployeeDetailsImpl(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities)
	{
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static EmployeeDetailsImpl build(Employees employee)
	{
		List<GrantedAuthority> authority = employee.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		
		return new EmployeeDetailsImpl(employee.getId(),employee.getEmail(),employee.getPassword(), authority);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		
		return this.authorities;
	}

	@Override
	public String getPassword()
	{
		return this.password;
	}

	@Override
	public String getUsername()
	{
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}

}
