package com.spring.security.example.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.spring.security.example.Entity.Employees;
import com.spring.security.example.Entity.Role;
import com.spring.security.example.ExceptionHandling.ResourceAlreadyExistsException;
import com.spring.security.example.JwtUtil.JwtUtil;
import com.spring.security.example.Model.LoginModel;
import com.spring.security.example.Model.SignupModel;
import com.spring.security.example.Repository.EmployeeRepository;
import com.spring.security.example.Repository.RoleRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService
{
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	ModelMapper mappper;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public Employees registerUser(SignupModel employeeSignup)
	{
		if(employeeRepository.findByEmail(employeeSignup.getEmail())!=null)
		{
			throw new ResourceAlreadyExistsException("Email already exists with address "+employeeSignup.getEmail());
		}
		else
		{
			Set<Role> roles = new HashSet<Role>();
			
			Employees employees=new Employees();
			
			Role userRole = roleRepository.findByName("USER");
			
			roles.add(userRole);
			
			employees = mappper.map(employeeSignup, Employees.class);
			
			employees.setPassword(passwordEncoder.encode(employeeSignup.getPassword()));

			employees.setRoles(roles);
			
			employeeRepository.save(employees);
			
			return employees;
		}
	}

	
	@Override
	public Employees registerAdmin(SignupModel employeeSignup)
	{
		if(employeeRepository.findByEmail(employeeSignup.getEmail())!=null)
		{
			throw new ResourceAlreadyExistsException("Email already exists with address "+employeeSignup.getEmail());
		}
		else
		{
			Set<Role> roles = new HashSet<Role>();
			
			Employees employees=new Employees();
			
			Role userRole = roleRepository.findByName("ADMIN");

			roles.add(userRole);
			
			employees = mappper.map(employeeSignup, Employees.class);
			
			employees.setPassword(passwordEncoder.encode(employeeSignup.getPassword()));


			employees.setRoles(roles);
			
			employeeRepository.save(employees);
			
			return employees;
		}
	}
	
	@Override
	public Object login(LoginModel employeeLogin)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(employeeLogin.getEmail(),employeeLogin.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwtToken = jwtUtil.generateJwtToken(authentication);
		
		EmployeeDetailsImpl userDetails = (EmployeeDetailsImpl) authentication.getPrincipal();
		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		
		data.put("jwtToken", jwtToken);
		data.put("username", userDetails.getUsername());
		data.put("password", userDetails.getPassword());
		data.put("roles", roles);
	
		return data;
	}
	
}
