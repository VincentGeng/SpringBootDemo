package com.example.service;

import com.example.domain.SystemUser;

public interface SystemUserService {

	void saveSystemUser(SystemUser systemUser);
	
	boolean checkIfSystemUserExistsByEmail(String email);

	boolean checkIfSystemUserExistsById(Long id);
	
	SystemUser getSystemUserById(long id);

}
