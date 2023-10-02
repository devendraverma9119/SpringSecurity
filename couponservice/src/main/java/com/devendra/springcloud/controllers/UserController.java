package com.devendra.springcloud.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.devendra.springcloud.model.Role;
import com.devendra.springcloud.model.User;
import com.devendra.springcloud.repos.UserRepo;
import com.devendra.springcloud.security.SecurityService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping("/showReg")
	public String showRegistrationPage() {
		return "registerUser";
	}
	
	@PostMapping("/registerUser")
	public String register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		Role role = new Role();
		role.setId((long) 1);
		role.setName("ROLE_ADMIN");
		user.setRoles(Set.of(role));
		userRepo.save(user);
		
		return "login";
	}
	
	@GetMapping("/")
	public String showLoginPage() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(String email,String password,HttpServletRequest request,HttpServletResponse response) {
		boolean result=securityService.login(email, password, request, response);
		if(result) {
			return "index";
		}
		return "login";
	}
}
