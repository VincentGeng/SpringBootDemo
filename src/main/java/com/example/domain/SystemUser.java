package com.example.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import groovy.transform.EqualsAndHashCode;

@Entity
@Table(name="user")
@EqualsAndHashCode(callSuper = true, excludes = { "id", "fullname", "username", "password", "status", "roles"})
public class SystemUser implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5761555019930249687L;
	
	private static final int ACTIVE_SYSTEM_USER = 1;
	
	//# ===============================
	//# = Column definition
	//# ===============================

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
	
	@Column(name = "fullname", columnDefinition="varchar(150)", nullable = false)
    private String fullname;

    @Column(name = "username", columnDefinition="varchar(150)", nullable = false, unique = true)
    private String username;

    @Column(name = "password", columnDefinition="varchar(150)", nullable = false)
    private String password;
    
    @Column(name = "status", columnDefinition="int default 1", nullable=false)
	private Integer status;
    
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private List<SystemRole> roles;

    
    //# ===============================
	//# = UserDetails implementation
	//# ===============================
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        List<SystemRole> roles = this.getRoles();
        for (SystemRole role : roles) {
            auths.add(new SimpleGrantedAuthority(role.getRolename()));
        }
        return auths;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		if(getStatus() == ACTIVE_SYSTEM_USER) {
			return true;
		}else{
			return false;
		}
		
	}
	
	//# ===============================
	//# = Constructor
	//# ===============================
	
	public SystemUser() {
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
	
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public List<SystemRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SystemRole> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
}
