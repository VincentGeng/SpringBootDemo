package com.example.web;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.domain.ResetPasswordFormDTO;
import com.example.domain.SystemUser;
import com.example.service.MailService;
import com.example.service.ResetPasswordTokenService;
import com.example.service.SystemUserService;

@Controller
public class PublicController extends BaseController{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    private static final int ACTIVE_SYSTEM_USER = 1;
    
    private static final String CANNOT_FIND_ACCOUNT = "1";
    private static final String TOKEN_INVALID = "2";
    private static final String TOKEN_EXPIRED = "3";
    
    @Autowired
    private SystemUserService systemUserService;
    
    @Autowired
    private ResetPasswordTokenService resetPasswordTokenService;
    
    @Autowired
    private MailService mailService;
    
    @Override
	protected String getModuleFolder() {
		return "public/";
	}

    @RequestMapping(value="/login",method = RequestMethod.GET)
    public ModelAndView loginPage(
    		@RequestParam Optional<String> successMsg,
    		@RequestParam Optional<String> error,
    		@RequestParam Optional<String> logout
    		) {
    	log.info("loginPage||GET|ENTRY");
    	log.info("loginPage||GET|Error Message is present:"+error.isPresent());
    	log.info("loginPage||GET|logout Message is present:"+logout.isPresent());
    	
    	if(error.isPresent()) {
    		
    		log.info("loginPage||GET|EXIT");
        	return new ModelAndView("public/login", "errorMsg", "Invalid username or password!");
        	
    	}else if(logout.isPresent()){
    		
    		log.info("loginPage||GET|EXIT");
        	return new ModelAndView("public/login", "successMsg", "You've been logged out successfully.");
        	
    	}else if(successMsg.isPresent()){
    		
    		log.info("loginPage||GET|EXIT");
        	return new ModelAndView("public/login", "successMsg", successMsg.get());
        	
    	}else{
    		
    		log.info("loginPage||GET|EXIT");
        	return goToCurrentFolderPage("login");
        	
    	}
		
    }
    
    @RequestMapping(value="/sign-up",method = RequestMethod.GET)
    public ModelAndView signUpPage(Model model
    		) {
    	log.info("sign-up||GET|ENTRY");
    	
    	model.addAttribute("systemUser", new SystemUser());
    	
    	log.info("sign-up||GET|EXIT");
    	return goToCurrentFolderPage("sign_up");
    }
    
    @RequestMapping(value="/sign-up",method = RequestMethod.POST)
    public ModelAndView signUpFormSubmit(
    		@ModelAttribute SystemUser systemUser
    		) {
    	systemUser.setStatus(ACTIVE_SYSTEM_USER);
    	systemUserService.saveSystemUser(systemUser);
    	return goToCurrentFolderPage("login");
    }
    
    @RequestMapping(value="/begin_reset_password",method = RequestMethod.GET)
    public ModelAndView beginResetPasswordPage(
    		@RequestParam Optional<String> errorMsg
    		) {
    	log.info("beginResetPasswordPage||GET|ENTRY");
    	log.info("beginResetPasswordPage||GET|Error Message: "+errorMsg);
    	
    	if(errorMsg.isPresent()) {
    		log.info("beginResetPasswordPage||GET|EXIT");
    		switch (errorMsg.get()) {
    		
	            case CANNOT_FIND_ACCOUNT:  
            			log.info("beginResetPasswordPage||GET|EXIT");
            			return new ModelAndView("public/begin_reset_password", "errorMsg", "We couldn't find your account with that information");
	            
	            case TOKEN_INVALID:  
		            	log.info("beginResetPasswordPage||GET|EXIT");
		    			return new ModelAndView("public/begin_reset_password", "errorMsg", "The reset link you clicked is invalid. Please request a new one.");
	            
	            case TOKEN_EXPIRED:
		            	log.info("beginResetPasswordPage||GET|EXIT");
		    			return new ModelAndView("public/begin_reset_password", "errorMsg", "The reset link you clicked has expired. Please request a new one.");
	            
	            default: 
	            		return new ModelAndView("public/begin_reset_password", "errorMsg", "The reset link you clicked encountered an unknown error. Please request a new one.");
    		}
    		
    	}else {
    		log.info("beginResetPasswordPage||GET|EXIT");
    		return goToCurrentFolderPage("begin_reset_password");
    	}
        
    }
    
    @RequestMapping(value="/begin_reset_password",method = RequestMethod.POST)
    public ModelAndView resetPasswordFormSubmit(
    		HttpServletRequest request,
    		@RequestParam String email
    		) {
    	log.info("beginResetPasswordPage||POST|ENTRY");
    	log.info("beginResetPasswordPage||POST|Email:"+email);
    	
    	if(systemUserService.checkIfSystemUserExistsByEmail(email)) {
    		log.info("beginResetPasswordPage||send email");
			log.info("beginResetPasswordPage||POST|EXIT");
    		return resetEmailSentPage(request, email);
    	}else {
    		log.info("beginResetPasswordPage||cannot find account");
			log.info("beginResetPasswordPage||POST|EXIT");
    		return beginResetPasswordPage(Optional.of(CANNOT_FIND_ACCOUNT));
    	}
    	
    }
    
    @RequestMapping(value="/reset_email_sent",method = RequestMethod.GET)
    public ModelAndView resetEmailSentPage(
    		HttpServletRequest request,
    		@RequestParam String email
    		) {
    	log.info("resetEmailSentPage||POST|ENTRY");
    	log.info("resetEmailSentPage||POST|Email:"+email);
    	
    	String resetPasswordLink = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/reset_password_form/";
    	
    	mailService.generateTokenAndSendResetPasswordMail(email, resetPasswordLink);
    	
    	log.info("resetEmailSentPage||POST|EXIT");
    	return new ModelAndView("public/reset_email_sent", "email", email.replaceAll("(\\w{1,2})(\\w+)(@.*)", "$1****$3"));
    	
    }
    
    @RequestMapping(value="/reset_password_form",method = RequestMethod.GET)
    public ModelAndView resetPasswordFormPage(
    		@RequestParam String token
    		) {
    	log.info("resetPasswordFormPage||GET|ENTRY");
    	log.info("resetPasswordFormPage||GET|Token:"+token);
    	
    	if(resetPasswordTokenService.checkIfTokenExistsByToken(token)) {
    		if(resetPasswordTokenService.checkIfTokenExpiredByToken(token)) {
    			ResetPasswordFormDTO resetPasswordFormDTO = new ResetPasswordFormDTO(resetPasswordTokenService.getSystemUserByToken(token), token);
    	    	log.info("resetPasswordFormPage||GET|EXIT");
    	    	return new ModelAndView("public/reset_password_form", "ResetPasswordFormDTO", resetPasswordFormDTO);
    		}else {
    			log.info("resetPasswordFormPage||token expired");
    			log.info("resetPasswordFormPage||GET|EXIT");
    			return beginResetPasswordPage(Optional.of(TOKEN_EXPIRED));
    		}
    	}else {
    		log.info("resetPasswordFormPage||invalid token");
    		log.info("resetPasswordFormPage||GET|EXIT");
    		return beginResetPasswordPage(Optional.of(TOKEN_INVALID));
    	}
    	
    }
    
    @RequestMapping(value="/reset_password_form",method = RequestMethod.POST)
    public ModelAndView resetPasswordFormSubmit(
    		@ModelAttribute ResetPasswordFormDTO resetPasswordFormDTO
    		) {
    	log.info("resetPasswordFormSubmit||POST|ENTRY");
    	String token = resetPasswordFormDTO.getToken();
    	log.info("resetPasswordFormSubmit||POST|Token:"+token);
    	
    	if(resetPasswordTokenService.checkIfTokenExistsByToken(token)) {
    		if(resetPasswordTokenService.checkIfTokenExpiredByToken(token)) {
    			
	    		systemUserService.saveSystemUser(resetPasswordFormDTO.getSystemUser());
	    		log.info("resetPasswordFormSubmit||POST|saved systemuser to database, witch id="+resetPasswordFormDTO.getSystemUser().getId());
	    		
	    		resetPasswordTokenService.deleteToken(token);
	    		log.info("resetPasswordFormSubmit||POST|token was deleted from database");
	    		
	        	log.info("resetPasswordFormSubmit||POST|EXIT");
	        	return new ModelAndView("public/login", "successMsg", "Your password has been updated.");
    		}else {
    			log.info("resetPasswordFormSubmit||token expired");
    			log.info("resetPasswordFormSubmit||POST|EXIT");
    			return beginResetPasswordPage(Optional.of(TOKEN_EXPIRED));
    		}
    	}else {
    		log.info("resetPasswordFormSubmit||invalid token");
    		log.info("resetPasswordFormSubmit||POST|EXIT");
    		return beginResetPasswordPage(Optional.of(TOKEN_INVALID));
    	}
    	
    }
    
}