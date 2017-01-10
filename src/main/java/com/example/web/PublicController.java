package com.example.web;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PublicController extends BaseController{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Override
	protected String getModuleFolder() {
		return "public/";
	}

    @RequestMapping(value="/login",method = RequestMethod.GET)
    public ModelAndView loginPage(@RequestParam Optional<String> error) {
    	log.info("loginPage||GET|ENTRY");
    	log.info("loginPage||GET|Error Message is present:"+error.isPresent());
    	if(error.isPresent()) {
    		log.info("loginPage||GET|EXIT");
        	return new ModelAndView("public/login", "error", error);
    	}else {
    		log.info("loginPage||GET|EXIT");
        	return goToCurrentFolderPage("login");
    	}
		
    }
    
    @RequestMapping(value="/login-error",method = RequestMethod.GET)
    public String loginErrorPage(
    		RedirectAttributes redirectAttrs,
    		Model model
    		) {
    	log.info("loginErrorPage||GET|ENTRY");
    	
    	redirectAttrs.addFlashAttribute("errorMsg", "Incorrect Email or Password!");
    	
		log.info("loginErrorPage||GET|EXIT");
    	return redirectToPage("/public/login");
    }
    
    @RequestMapping(value="/reset-password",method = RequestMethod.GET)
    public ModelAndView resetPasswordPage(
    		) {
    	return goToCurrentFolderPage("reset_password");
    }
    
}