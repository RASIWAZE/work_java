package com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@IdClass(RoleId.class)
@Entity
@Table(name="roles")
public class Role {

    @Id  // 主キーである複合キーに含まれるカラムに対応するフィールド
    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;
    
    @Id  // 主キーである複合キーに含まれるカラムに対応するフィールド
    @Column(name="role", nullable = false, length = 50)
    private String role;
    
    @ManyToOne(fetch = FetchType.EAGER,
    		   cascade={CascadeType.PERSIST, CascadeType.MERGE,
    				    CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	@JsonBackReference
	private User user;
	
	public Role() {
		
	}

	public Role(String userId, String role) {
		this.userId = userId;
		this.role = role;
	}

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
		return "ユーザー名=" + userId + ", 権限=" + role;
	}
		
}
