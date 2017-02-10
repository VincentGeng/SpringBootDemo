package com.example.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="persistent_logins")
public class PersistentLogins {
	
	//# ===============================
	//# = Column definition
	//# ===============================
	
    @Column(name = "username", columnDefinition="varchar(64)", nullable=false)
    private String username;
    
    @Id
    @Column(name = "series", columnDefinition="varchar(64)")
    private String series;
    
    @Column(name = "token", columnDefinition="varchar(64)", nullable=false)
    private String token;
    
	@Column(name = "last_used", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Type(type="timestamp")
	private Date lastUsed;

}
