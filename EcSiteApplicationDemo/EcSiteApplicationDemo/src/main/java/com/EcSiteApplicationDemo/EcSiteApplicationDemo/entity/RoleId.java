package com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity;

import java.io.Serializable;

public class RoleId implements Serializable {
	
	private String userId;

	private String role;

	
	public String getUserId() {
		return userId;
	}

	public String getRole() {
		return role;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "IdRole [userId=" + userId + ", role=" + role + "]";
	}

}
