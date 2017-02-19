package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.SystemUser;
import com.example.repository.SystemUserRepository;
import com.example.service.SystemUserService;

@Transactional
@Service
public class SystemUserServiceImpl implements SystemUserService{
	
	private SystemUserRepository systemUserRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
    public SystemUserServiceImpl(
    		SystemUserRepository systemUserRepository,
    		BCryptPasswordEncoder bCryptPasswordEncoder
    		) {
		
        this.systemUserRepository = systemUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        
    }

	@Override
	public void saveSystemUser(SystemUser systemUser) {
		systemUser.setPassword(bCryptPasswordEncoder.encode(systemUser.getPassword()));
		systemUserRepository.save(systemUser);
	}

	@Override
	public boolean checkIfSystemUserExistsByEmail(String email) {
		return systemUserRepository.findByUsername(email).isPresent();
	}

	@Override
	public boolean checkIfSystemUserExistsById(Long id) {
		return systemUserRepository.findOne(id)!=null;
	}

	@Override
	public SystemUser getSystemUserById(long id) {
		return systemUserRepository.findOne(id);
	}

}
