package com.devendra.springcloud.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.devendra.springcloud.security.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig{ // extends WebSecurityConfigurerAdapter 

//	@Autowired
//	UserDetailsServiceImpl userDetailsService;
//	
//	@Overrides
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//	auth.userDetailsService(userDetailsService);
//	}
	
	
//	protected void configure(HttpSecurity http) throws Exception {
//		http.httpBasic();
//		http.authorizeRequests().requestMatchers(HttpMethod.GET,"/couponapi/coupons").hasAnyRole("USER","ADMIN")
//		.requestMatchers(HttpMethod.POST,"/couponapi/coupons")
//		.hasRole("ADMIN").and().csrf().disable();
//	}
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityContextRepository securityContextRepository() {
		return new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(),new HttpSessionSecurityContextRepository());
	}
	
	@Bean
	AuthenticationManager authManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(provider);
	}
	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	//	http.formLogin();
		http.authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/couponapi/coupons/{code:^[A-Z]*$}","/showGetCoupon","/getCoupon").hasAnyRole("USER","ADMIN")    ///couponapi/coupons/**
		.requestMatchers(HttpMethod.GET,"/showCreateCoupon","/createCoupon","/createResponse").hasRole("ADMIN")		
		.requestMatchers(HttpMethod.POST,"/couponapi/coupons","/saveCoupon" ).hasRole("ADMIN")
		.requestMatchers(HttpMethod.POST,"/getCoupon" ).hasAnyRole("USER","ADMIN")
		.requestMatchers("/","/login","/showReg","/registerUser").permitAll()
		.and().logout().logoutSuccessUrl("/");
		//.and().csrf().disable();
		
		http.securityContext((securityContext)-> securityContext.requireExplicitSave(true));
		
		return http.build();
	}
	
}
