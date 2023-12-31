package com.group10.contestPlatform.dtos.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class SignUpForm {
	private String email;
	private String username;
	private String firstName;

	private String lastName;
	
	private String password;
	private Set<String> roles;

	 public SignUpForm() {
    }

	 @JsonCreator
    public SignUpForm(@JsonProperty("username") String username, @JsonProperty("lastName") String lastName, @JsonProperty("firstName") String firstName,
                      @JsonProperty("email") String email,
                      @JsonProperty("password") String password, @JsonProperty("roles") Set<String> roles) {
        this.username = username;
        this.lastName = lastName;
        this.email = email;
        this.firstName = firstName;
        this.password = password;
        this.roles = roles;
	    }


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}
