package com.spring.security.example.Controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.spring.security.example.Model.LoginModel;
import com.spring.security.example.Model.ResponseModel;
import com.spring.security.example.Model.SignupModel;
import com.spring.security.example.Service.EmployeeService;

@RestController
public class AuthController extends BaseController
{
	@Autowired
	EmployeeService employeeService;
	
	
	@PostMapping("/register/user")
	public ResponseEntity<ResponseModel> registerUser(@Valid @RequestBody SignupModel model)
	{
		ResponseModel responseModel = ResponseModel.getInstance();
		responseModel.setData(employeeService.registerUser(model));
		responseModel.setMessage("Employee Registered Successfully");
		responseModel.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseModel>(responseModel,HttpStatus.ACCEPTED);
	}
	@PostMapping("/register/admin")
	public ResponseEntity<ResponseModel> registerAdmin(@Valid @RequestBody SignupModel model)
	{
		ResponseModel responseModel = ResponseModel.getInstance();
		responseModel.setData(employeeService.registerAdmin(model));
		responseModel.setMessage("Employee Registered Successfully");
		responseModel.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseModel>(responseModel,HttpStatus.ACCEPTED);
	}
	@PostMapping("/login")
	public ResponseEntity<ResponseModel> login(@Valid @RequestBody LoginModel login)
	{
		ResponseModel model = ResponseModel.getInstance();
		model.setData(employeeService.login(login));
		model.setMessage("Employee login successfull");
		model.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseModel>(model,HttpStatus.ACCEPTED);
	}
}
