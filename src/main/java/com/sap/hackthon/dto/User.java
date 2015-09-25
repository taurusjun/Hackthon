package com.sap.hackthon.dto;

import java.io.Serializable;

/**
 * 
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = -2896255157637535567L;
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * password
	 * 
	 * @return
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * password
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
