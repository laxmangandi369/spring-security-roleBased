package com.spring.security.example.Service;

import com.spring.security.example.Entity.Employees;
import com.spring.security.example.Model.LoginModel;
import com.spring.security.example.Model.SignupModel;

public interface EmployeeService
{
	Employees registerUser(SignupModel employeeSignup);
	Employees registerAdmin(SignupModel employeeSignup);
	Object login(LoginModel employeeLogin);
}
