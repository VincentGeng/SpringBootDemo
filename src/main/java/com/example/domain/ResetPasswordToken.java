package com.example.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name="reset_password_token" )
public class ResetPasswordToken implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2095104425032001063L;
	
	//# ===============================
	//# = Column definition
	//# ===============================

	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "token_id", columnDefinition="bigint")
	private Long tokenId;
	
	@Column(name = "email", columnDefinition="varchar(150)", nullable=false)
	private String email;
	
	@Column(name = "token", columnDefinition="varchar(50)", nullable=false)
	private String token;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false)
	private Date createdDate;


	//# ===============================
	//# = Constructor
	//# ===============================
    
	public ResetPasswordToken() {
		super();
	}
    
    //# ===============================
  	//# = Getter & Setter
  	//# ===============================

	public Long getTokenId() {
		return tokenId;
	}

	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
