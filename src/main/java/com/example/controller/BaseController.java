package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public abstract class BaseController {
	
	/**
	 * 
	 * @return subfolder located in "/WEB-INF/views/" for this current module
	 */
	
	protected abstract String getModuleFolder();
	
	protected String redirectToPage(String pagePath) {
		return "redirect:/" + pagePath;
	}
	
	protected ModelAndView goToCurrentFolderPage(String pageName) {
		return new ModelAndView(getModuleFolder() + pageName);
	}
	
	protected String getLoginPath() {
		return "login";
	}
	
	protected String getRedirectedLoginPath() {
		return "redirect:/"+getLoginPath();
	}
	
	protected String redirect(String url) {
		return "redirect:"+url;
	}

}
