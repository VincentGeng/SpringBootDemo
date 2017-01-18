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
        	return new ModelAndView("public/login", "logoutMsg", "You've been logged out successfully.");
        	
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
    
    @RequestMapping(value="/forgot-password",method = RequestMethod.GET)
    public ModelAndView forgotPasswordPage(
    		@RequestParam Optional<String> error,
    		@RequestParam Optional<String> message
    		) {
    	log.info("forgotPasswordPage||GET|ENTRY");
    	log.info("forgotPasswordPage||GET|Error is present:"+error.isPresent());
    	log.info("forgotPasswordPage||GET|Message is present:"+message.isPresent());
    	
    	if(error.isPresent()) {
    		
    		log.info("forgotPasswordPage||GET|EXIT");
        	return new ModelAndView("public/forgot_password", "error", error.get());
        	
    	}else if(message.isPresent()){
    		
    		log.info("forgotPasswordPage||GET|EXIT");
        	return new ModelAndView("public/forgot_password", "message", message.get());
        	
    	}else{
    		
    		log.info("forgotPasswordPage||GET|EXIT");
    		return goToCurrentFolderPage("forgot_password");
        	
    	}
    	
    }
    
}