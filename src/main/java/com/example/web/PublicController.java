package com.example.web;

import java.util.Optional;

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

import com.example.domain.SystemUser;
import com.example.service.SystemUserService;

@Controller
public class PublicController extends BaseController{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    private static final int ACTIVE_SYSTEM_USER = 1;
    
    @Autowired
    private SystemUserService systemUserService;
    
    @Override
	protected String getModuleFolder() {
		return "public/";
	}

    @RequestMapping(value="/login",method = RequestMethod.GET)
    public ModelAndView loginPage(
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
    		return new ModelAndView("public/begin_reset_password", "errorMsg", errorMsg.get());
    	}else {
    		log.info("beginResetPasswordPage||GET|EXIT");
    		return goToCurrentFolderPage("begin_reset_password");
    	}
        
    }
    
    @RequestMapping(value="/begin_reset_password",method = RequestMethod.POST)
    public ModelAndView resetPasswordFormSubmit(
    		@RequestParam String email
    		) {
    	log.info("beginResetPasswordPage||POST|ENTRY");
    	log.info("beginResetPasswordPage||POST|Email:"+email);
    	
    	if(systemUserService.checkIfSystemUserExistsByEmail(email)) {
    		//resetPasswordEmailService
    		return resetEmailSentPage(email);
    	}else {
    		return beginResetPasswordPage(Optional.of("We couldn't find your account with that information"));
    	}
    	
    }
    
    @RequestMapping(value="/reset_email_sent",method = RequestMethod.GET)
    public ModelAndView resetEmailSentPage(
    		@RequestParam String email
    		) {
    	log.info("resetEmailSentPage||POST|ENTRY");
    	log.info("resetEmailSentPage||POST|Email:"+email);
    	
    	return new ModelAndView("public/reset_email_sent", "email", email.replaceAll("(\\w{1,2})(\\w+)(@.*)", "$1****$3"));
    	
    }
    
}