package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.SystemUser;
import com.example.repository.ResetPasswordTokenRepository;
import com.example.repository.SystemUserRepository;
import com.example.service.ResetPasswordTokenService;

@Transactional
@Service
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService{
	
	private ResetPasswordTokenRepository resetPasswordTokenRepository;
	private SystemUserRepository systemUserRepository;
	
	@Autowired
    public ResetPasswordTokenServiceImpl(ResetPasswordTokenRepository resetPasswordTokenRepository,  SystemUserRepository systemUserRepository) {
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
        this.systemUserRepository = systemUserRepository;
    }

	@Override
	public boolean checkIfTokenExistsByToken(String token) {
		return resetPasswordTokenRepository.findByToken(token).isPresent();
	}

	@Override
	public SystemUser getSystemUserByToken(String resetPasswordToken) {
		String email = resetPasswordTokenRepository
											.findByToken(resetPasswordToken)
											.get()
											.getEmail();
		 
		return systemUserRepository.findByUsername(email).get();
	}
}
