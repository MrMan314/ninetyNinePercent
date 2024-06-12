package com.ninetyninepercentcasino.net;

/**
 * Exception for a LoginError, sent by the server to the client instead of UserNotFound or PasswordIncorrect to prevent brute force attacks
 */
public class LoginError extends Exception {
	public LoginError() {
		super();
	}
}
