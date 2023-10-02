package com.devendra.springcloud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.devendra.springcloud.model.Coupon;
import com.devendra.springcloud.repos.CouponRepo;

@RestController
@RequestMapping("/couponapi")
@CrossOrigin
public class CouponRestController {
	
	@Autowired
	CouponRepo repo ;
	
	@PostMapping(value = "/coupons")
	public Coupon create(@RequestBody Coupon coupon) {
		return repo.save(coupon);
	}

	@GetMapping(value = "/coupons/{code}")  //@PostAuthorize("returnObject.discount<60")
	public Coupon getCoupon(@PathVariable String code) {
		return repo.findByCode(code);
	}

}
