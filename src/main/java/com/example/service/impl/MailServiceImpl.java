package com.example.service.impl;

import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.domain.ResetPasswordToken;
import com.example.repository.ResetPasswordTokenRepository;
import com.example.service.MailService;

@Service
public class MailServiceImpl implements MailService{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public static final String SUBJECT_RESET_PASSWORD_USER = "Reset Password for Your Account";
	
	public static final String SENDER_NAME = "Spring Boot Demo";
    public static final String SENDER_EMAIL = "demo@springboot.com.sg";
	
    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;
    private ResetPasswordTokenRepository resetPasswordTokenRepository;
	
	@Autowired
	MailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine, ResetPasswordTokenRepository resetPasswordTokenRepository) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    }
	
	@Async
    public void generateTokenAndSendResetPasswordMail(String email, String resetPasswordLink){
		log.info("sendResetPasswordMail || ENTRY");
		
		String token = UUID.randomUUID().toString();
		resetPasswordLink += "?token="+token;
		log.debug("sendResetPasswordMail || ResetPasswordLink: "+resetPasswordLink);
		
		ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
		
		resetPasswordToken.setToken(token);
		resetPasswordToken.setEmail(email);
		resetPasswordTokenRepository.save(resetPasswordToken);
		
		Context context = new Context();
        context.setVariable("user", email);
        context.setVariable("reset_password_link", resetPasswordLink);

        String emailContent = templateEngine.process("emails/reset_password", context);
        log.info("sendResetPasswordMail || EXIT");
        this.sendMail(email, SUBJECT_RESET_PASSWORD_USER, emailContent, true, null);
    }
	
	@Async
    private void sendMail(String email, String subject, String content, Boolean isHtml, String filepath) {
        String defaultSender = SENDER_NAME;
        String defaultSenderEmail = SENDER_EMAIL;

        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true, "utf-8");
            helper.setTo(email);
//            String[] setEmail = {"mengxin.geng@techstudio.com.sg"};
//            helper.setTo(setEmail);
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
