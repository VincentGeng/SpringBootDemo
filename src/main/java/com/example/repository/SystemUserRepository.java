package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.SystemUser;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long>{
	
	Optional<SystemUser> findByUsername(String username);
	
}
