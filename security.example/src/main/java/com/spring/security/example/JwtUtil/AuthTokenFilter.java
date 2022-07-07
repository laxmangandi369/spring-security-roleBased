package com.spring.security.example.JwtUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.spring.security.example.Service.EmployeeDetailService;
import io.jsonwebtoken.Claims;

public class AuthTokenFilter extends OncePerRequestFilter
{
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private EmployeeDetailService detailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
	{
		try
		{
			String jwt = parseJwt(request);
			if (jwt != null)
			{
				Claims claims = jwtUtil.getJwtClaims(jwt);
				String username = claims.getSubject();

				UserDetails userDetails = detailService.loadUserByUsername(username);

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, getAuthoritiesFromString(claims));
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		catch (Exception e)
		{
			logger.error("cannot set authentication: {}", e);
		}

		filterChain.doFilter(request, response);

	}

	private Collection<? extends GrantedAuthority> getAuthoritiesFromString(Claims claims)
	{
		return Arrays.stream(claims.get("roles").toString().split(","))
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	private String parseJwt(HttpServletRequest request)
	{
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer"))
		{
//			return headerAuth.substring(7, headerAuth.length());
			return headerAuth.replaceAll("Bearer", "");
		}
		return null;
	}
}
