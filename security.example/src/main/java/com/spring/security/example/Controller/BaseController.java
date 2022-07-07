package com.spring.security.example.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/employee")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BaseController
{

}
