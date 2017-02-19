package com.example.domain;

public class MyProfileFormDTO {
	
	private SystemUser systemUser;
    private String successMsg;
    private String errorMsg;
    
    //# ===============================
	//# = Constructor
	//# ===============================
    
    public MyProfileFormDTO() {
    	
    }

	public MyProfileFormDTO(SystemUser systemUser, String successMsg, String errorMsg) {
		super();
		this.systemUser = systemUser;
		this.successMsg = successMsg;
		this.errorMsg = errorMsg;
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

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}

