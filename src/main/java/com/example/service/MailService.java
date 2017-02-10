package com.example.service;

public interface MailService {
	public void generateTokenAndSendResetPasswordMail(String email, String content);
}
