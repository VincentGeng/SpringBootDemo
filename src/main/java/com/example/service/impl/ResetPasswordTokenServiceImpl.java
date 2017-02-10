package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.repository.ResetPasswordTokenRepository;
import com.example.service.ResetPasswordTokenService;

@Transactional
@Service
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService{
	
	private ResetPasswordTokenRepository resetPasswordTokenRepository;
	
	@Autowired
    public ResetPasswordTokenServiceImpl(ResetPasswordTokenRepository resetPasswordTokenRepository) {
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    }

	@Override
	public boolean checkIfTokenExistsByToken(String token) {
		return resetPasswordTokenRepository.findByToken(token).isPresent();
	}
}
