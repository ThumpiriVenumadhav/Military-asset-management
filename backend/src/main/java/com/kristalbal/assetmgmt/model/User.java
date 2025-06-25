package com.kristalbal.assetmgmt.model;

import jakarta.persistence.*;
import lombok.*;

@Entity

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role; // ADMIN, COMMANDER, LOGISTICS

    @ManyToOne
    @JoinColumn(name = "base_id")
    private Base base;
    
    
    public User() {
		// TODO Auto-generated constructor stub
	}


	public User(Long id, String username, String password, String role, Base base) {
		
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.base = base;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public Base getBase() {
		return base;
	}


	public void setBase(Base base) {
		this.base = base;
	}
	
	
    
    
}
