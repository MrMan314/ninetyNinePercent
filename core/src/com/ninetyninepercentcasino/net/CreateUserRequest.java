package com.ninetyninepercentcasino.net;

/**
 * DTO that the client sends to the server to inform them about creating a user
 */
public class CreateUserRequest extends DTO {
	private String username;
	private String password;

	/**
	 * Initializes the DTO with the user and password fields
	 */
	public CreateUserRequest(String username, String password) {
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
