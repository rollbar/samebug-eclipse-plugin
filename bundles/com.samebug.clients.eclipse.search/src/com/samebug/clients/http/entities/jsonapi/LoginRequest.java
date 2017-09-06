package com.samebug.clients.http.entities.jsonapi;

public class LoginRequest {
	
	String type="signin-request";
	String email; 
	String password;
	
	public LoginRequest(String email, String password) {
		this.email=email;
		this.password=password;
	}
}
