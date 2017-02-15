package com.example.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.ResetPasswordToken;
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
	
	@Value("${token.expired.duration}")
    private String TOKEN_EXPIRED_DURATION;

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

	@Override
	public boolean checkIfTokenExpiredByToken(String resetPasswordToken) {
		ResetPasswordToken token = resetPasswordTokenRepository.findByToken(resetPasswordToken).get();
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(new Date()); // sets calendar time/date
		if ( cal.getTime().getTime() - token.getCreatedDate().getTime() >= Long.valueOf(TOKEN_EXPIRED_DURATION)) {
	    	return false;
	    }else {
	    	return true;
	    }
	}

	@Override
	public void deleteToken(String resetPasswordToken) {
		resetPasswordTokenRepository.deleteByToken(resetPasswordToken);
	}
}
