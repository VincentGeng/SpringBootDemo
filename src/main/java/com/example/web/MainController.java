package com.example.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class MainController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/")
    public String main(Model model, Authentication authentication) {
		
        log.info("main|||GET|ENTRY");
        
        model.addAttribute("header", "Dashboard");
        
        log.info("main|||GET|EXIT");
        
        return "dashboard";
    }
}
