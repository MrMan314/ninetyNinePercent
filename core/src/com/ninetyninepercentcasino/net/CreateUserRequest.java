package com.ninetyninepercentcasino.net;

public class CreateUserRequest extends DTO {
	private String username;
	private String password;
	public CreateUserRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
}
