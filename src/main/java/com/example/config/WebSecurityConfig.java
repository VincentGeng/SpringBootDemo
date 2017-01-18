package com.example.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.example.security.CustomUserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
    private CustomUserService customUserService;
	
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Add customised UserDetailService authentication.
        auth
        	.userDetailsService(customUserService)
	        .and()
	        .inMemoryAuthentication()
	        .withUser("user@demo.com").password("pass").roles("USER");
    }
	
	
	/* ************************************************************************
	 * Migration: Spring Security 3 to Spring Security 4
	 * Document: http://docs.spring.io/spring-security/site/migrate/current/3-to-4/html5/migrate-3-to-4-xml.html#m3to4-xmlnamespace-form-login
	 * Description: 
	 * 1. .formLogin()
	 * 		1.1 .loginProcessingUrl(loginProcessingUrl) -> default value of "loginProcessingUrl"
	 * 			Spring Security 3: "/j_spring_security_check", html form action "/j_spring_security_check" 
	 * 			-> 
	 * 			Spring Security 4: "/login", html form action "/login", form method "POST" 
	 * 		1.2 .usernameParameter(usernameParameter) -> default value of "usernameParameter"
	 * 			Spring Security 3: "j_username", html form input field name "j_username"
	 * 			-> 
	 * 			Spring Security 4: "username", html form input field name "username"
	 * 		1.3 .passwordParameter(passwordParameter) -> default value of "passwordParameter"
	 * 			Spring Security 3: "j_password", html form input field name "j_password" 
	 * 			-> 
	 * 			Spring Security 4: "password", html form input field name "password"
	 * 		1.4 .failureUrl(authenticationFailureUrl) -> default value of "authenticationFailureUrl"
	 * 			Spring Security 3: appending ?login_error to the login-page 
	 * 			-> 
	 * 			Spring Security 4: appending ?error to the login-page
	 * 2 .logout()
	 * 		2.1 .logoutUrl(logoutUrl) -> default value of "logoutUrl"
	 * 			Spring Security 3: "/j_spring_security_logout"
	 * 			-> 
	 * 			Spring Security 4: "/logout"
	 * 		2.2 .logoutSuccessUrl(logoutSuccessUrl) -> default value of "logoutSuccessUrl"
	 * 			Spring Security 3: "/"
	 * 			-> 
	 * 			Spring Security 4: "/login?logout"
	 ************************************************************************ */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        		.csrf().disable()
        		
        		.authorizeRequests()
        		.antMatchers("/login", "/sign-up", "/forgot-password").permitAll()
                .anyRequest().authenticated()
                
                .and()
                	.formLogin()
                    .loginPage("/login")
                    .permitAll()
                    
                .and()
	                .logout()
	                .permitAll()
	                
                .and()
	                .rememberMe()
	                .tokenRepository(persistentTokenRepository())// persistent token for "remember me"
	                .tokenValiditySeconds(86400);// keep for one day;
    }
    
    
    /* ************************************************************************
	 * Description: Ignore static resource
	 ************************************************************************ */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
        	.ignoring()
        	.antMatchers("/bootstrap/**", "/dist/**", "/plugins/**", "/public/**");
    }
    
    @Bean(name="persistentTokenRepository")
    public PersistentTokenRepository persistentTokenRepository() {
    	JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
    	tokenRepository.setDataSource(dataSource);
    	return tokenRepository;
    }
    
}
