package com.group10.contestPlatform.dtos.auth;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponese {
	private String token;
	private String type = "Bearer";
	private String username;
	private String first_name;
	private String last_name;
	private Collection<? extends GrantedAuthority> roles;
	
	

	public JwtResponese() {
	}

	public JwtResponese(String token, String type, String username, String first_name, String last_name, Collection<? extends GrantedAuthority> roles) {
		this.token = token;
		this.type = type;
		this.username = username;
		this.first_name = first_name;
		this.last_name = last_name;
		this.roles = roles;
	}

	public JwtResponese(String token, String username, String firstName, String lastName, Collection<? extends GrantedAuthority> authorities) {
		this.token = token;
		this.username = username;
		this.first_name = firstName;
		this.last_name = lastName;
		this.roles = authorities;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Collection<? extends GrantedAuthority> getRoles() {
		return roles;
	}

	public void setRoles(Collection<? extends GrantedAuthority> roles) {
		this.roles = roles;
	}
	 
	 
}
