package com.devendra.springcloud.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devendra.springcloud.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

//	Product findByCode(String code);

}
