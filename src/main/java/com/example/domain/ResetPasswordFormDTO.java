package com.example.domain;

public class ResetPasswordFormDTO {
	
	private SystemUser systemUser;
    private String token;
    
    //# ===============================
	//# = Constructor
	//# ===============================
    
    public ResetPasswordFormDTO() {
    	
    }

	public ResetPasswordFormDTO(SystemUser systemUser, String token) {
		super();
		this.systemUser = systemUser;
		this.token = token;
	}
	
	//# ===============================
  	//# = Getter & Setter
  	//# ===============================

	public SystemUser getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
 
    
    
}

