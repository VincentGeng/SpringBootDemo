package com.example.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class SystemRole implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7089487531105476937L;

	//# ===============================
	//# = Column definition
	//# ===============================

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rolename", columnDefinition="varchar(150)", nullable = false, unique = true)
    private String rolename;
    
    //# ===============================
	//# = Constructor
	//# ===============================
    
    public SystemRole() {
		super();
	}
    
    //# ===============================
  	//# = Getter & Setter
  	//# ===============================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
    

}
