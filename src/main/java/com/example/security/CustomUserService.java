package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.domain.SystemUser;
import com.example.repository.SystemUserRepository;

@Service
public class CustomUserService implements UserDetailsService {
	
	private SystemUserRepository systemUserRepository;
	
	@Autowired
    public CustomUserService(SystemUserRepository systemUserRepository) {
        this.systemUserRepository = systemUserRepository;
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		SystemUser user = systemUserRepository.findByUsername(username)
        		.orElseThrow(() -> new UsernameNotFoundException(String.format("User with username=%s was not found", username)));
		
		return user;
		
	}

}
