package com.example.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.domain.SystemUser;
import com.example.domain.MyProfileFormDTO;
import com.example.service.SystemUserService;

@RestController
@RequestMapping(value="/profile")
public class ProfileController extends BaseController{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private SystemUserService systemUserService;
	
	@Autowired
	public ProfileController(SystemUserService systemUserService) {
		this.systemUserService = systemUserService;
	}

	@Override
	protected String getModuleFolder() {
		return "profile/";
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public ModelAndView getProfile(@PathVariable long id){
		log.info("getProfile||POST|ENTRY");
		log.info("getProfile||POST|Profile ID: "+id);
		
		if(this.systemUserService.checkIfSystemUserExistsById(id)){
			log.info("getProfile||POST|EXIT");
			return new ModelAndView("profile/detail", "MyProfileFormDTO", new MyProfileFormDTO(this.systemUserService.getSystemUserById(id), null, null));
	    }else{
		    log.info("getProfile||POST|EXIT");
		    return new ModelAndView("profile/detail", "MyProfileFormDTO", new MyProfileFormDTO(this.systemUserService.getSystemUserById(id), null, "Your account doesn't exist."));
	    }
		
	}
	
	@RequestMapping(value="/update",method = RequestMethod.PUT)
	public ModelAndView updateProfile(@RequestBody SystemUser systemUser) throws Exception {
	 
	   if(this.systemUserService.checkIfSystemUserExistsById(systemUser.getId())){
		   this.systemUserService.saveSystemUser(systemUser);
		   return new ModelAndView("profile/detail", "MyProfileFormDTO", new MyProfileFormDTO(systemUser, "Your account has been updated.", null));
	   }else{
	       return new ModelAndView("profile/detail", "MyProfileFormDTO", new MyProfileFormDTO(systemUser, null, "Your account doesn't exist."));
	   }
	}
	
}