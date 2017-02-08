package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.SystemUser;
import com.example.repository.SystemUserRepository;
import com.example.service.SystemUserService;

@Transactional
@Service
public class SystemUserServiceImpl implements SystemUserService{
	
	private SystemUserRepository systemUserRepository;
	
	@Autowired
    public void setSystemUserRepository(SystemUserRepository systemUserRepository) {
        this.systemUserRepository = systemUserRepository;
    }

	@Override
	public void saveSystemUser(SystemUser systemUser) {
		systemUserRepository.save(systemUser);
	}

	@Override
	public boolean checkIfSystemUserExistsByEmail(String email) {
		return systemUserRepository.findByUsername(email).isPresent();
	}

}
