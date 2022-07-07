package com.spring.security.example.Security;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.spring.security.example.JwtUtil.AuthEntryPointJwt;
import com.spring.security.example.JwtUtil.AuthTokenFilter;
import com.spring.security.example.Service.EmployeeDetailService;

@SuppressWarnings("deprecation")
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(
		  prePostEnabled = true,
		  securedEnabled = true,
		  jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	@Autowired
	private EmployeeDetailService detailService;
	
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
//	@Autowired
//	PasswordEncoder encoder;
	
	@Bean
	public AuthTokenFilter authTokenFiltera()
	{
		return new AuthTokenFilter();
	}
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(detailService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public ModelMapper modelMapper()
	{
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		return mapper;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.cors().and().csrf().disable()
//		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests()
//		.antMatchers("/employee/admin").hasRole("ADMIN")
//		.antMatchers("/employee/user").hasAnyRole("ADMIN","USER")
		.antMatchers("/employee/login").permitAll()
		.antMatchers("/employee/all").permitAll()
		.antMatchers("/employee/register/admin").permitAll()
		.antMatchers("/employee/register/user").permitAll()
		.anyRequest().authenticated();
		
		http.addFilterBefore(authTokenFiltera(), UsernamePasswordAuthenticationFilter.class);
	}
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder()
//	{
//		return new BCryptPasswordEncoder();
//	}
}
