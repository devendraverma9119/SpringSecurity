package com.devendra.springcloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class SecurityServiceImpl implements SecurityService {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	SecurityContextRepository securityContextRepository;

	public boolean login(String userName, String password,HttpServletRequest request,HttpServletResponse response) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName,password,userDetails.getAuthorities());
		authenticationManager.authenticate(token);
		boolean result = token.isAuthenticated();
		if(result) {
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(token);
			securityContextRepository.saveContext(securityContext, request, response);
		}
		return result;
	}


}
