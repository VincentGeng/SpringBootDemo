package com.example.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.domain.SystemUser;
import com.example.repository.SystemUserRepository;

@Service
public class CustomUserService implements UserDetailsService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private SystemUserRepository systemUserRepository;
	
	@Autowired
    public CustomUserService(SystemUserRepository systemUserRepository) {
        this.systemUserRepository = systemUserRepository;
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("CustomUserService||loadUserByUsername|GET|ENTRY");
		log.info("CustomUserService||loadUserByUsername|GET|username :"+username);
		
		SystemUser user = systemUserRepository.findByUsername(username)
        		.orElseThrow(() -> new UsernameNotFoundException(String.format("User with username=%s was not found", username)));
		
		log.info("CustomUserService||loadUserByUsername|GET|ENTRY");
		return user;
		
	}

}
