package com.example.service;

public interface ResetPasswordTokenService {

	boolean checkIfTokenExistsByToken(String token);

}
