package com.devendra.springcloud.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devendra.springcloud.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

}
