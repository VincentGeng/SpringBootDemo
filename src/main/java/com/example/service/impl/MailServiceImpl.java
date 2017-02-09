package com.example.service.impl;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.service.MailService;

@Service
public class MailServiceImpl implements MailService{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public static final String SUBJECT_RESET_PASSWORD_USER = "Reset Password for Your Account";
	
	public static final String SENDER_NAME = "Spring Boot Demo";
    public static final String SENDER_EMAIL = "demo@springboot.com.sg";
	
    private JavaMailSender javaMailSender;
	
	@Autowired
	MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
	
	@Async
    public void sendResetPasswordMail(String email, String content){
        this.sendMail(email, SUBJECT_RESET_PASSWORD_USER, content, true, null);
    }
	
    private void sendMail(String email, String subject, String content, Boolean isHtml, String filepath) {
        String defaultSender = SENDER_NAME;
        String defaultSenderEmail = SENDER_EMAIL;

        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true, "utf-8");
            //helper.setTo(email);
            String[] setEmail = {"vincentgeng90@gmail.com"};
            helper.setTo(setEmail);
            helper.setFrom(defaultSenderEmail, defaultSender);
            helper.setSubject(subject);
            helper.setText(content, isHtml);

            javaMailSender.send(mail);
            log.info("sendMail || Successfully send email '{}' to: {}", subject, email);
        } catch (Exception e) {
            log.error("sendMail || Problem with sending email to: {}, error message: {}", email, e.getMessage());
        }
    }
	
}
