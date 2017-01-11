package com.example.web;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PublicController extends BaseController{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
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
    
    @RequestMapping(value="/reset-password",method = RequestMethod.GET)
    public ModelAndView resetPasswordPage(
    		) {
    	return goToCurrentFolderPage("reset_password");
    }
    
}