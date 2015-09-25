package com.sap.hackthon.dto;

import java.io.Serializable;

/**
 * 
 * 
 */
public class SessionInfo implements Serializable {

	private static final long serialVersionUID = -2896255157637535567L;
	private String username;
	private String tenantName;

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


}
