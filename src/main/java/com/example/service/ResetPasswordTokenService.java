package com.example.service;

import com.example.domain.SystemUser;

public interface ResetPasswordTokenService {

	boolean checkIfTokenExistsByToken(String token);

	SystemUser getSystemUserByToken(String resetPasswordToken);

}
