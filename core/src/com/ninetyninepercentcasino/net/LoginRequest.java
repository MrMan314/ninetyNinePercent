package com.ninetyninepercentcasino.net;

/**
 * DTO that the client sends to the server to login
 */
public class LoginRequest extends DTO {
	private String username;
	private String password;

	/**
	 * Initializes the DTO with the user and password fields
	 */
	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Returns the username of the object
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the password of the object
	 */
	public String getPassword() {
		return password;
	}
}
