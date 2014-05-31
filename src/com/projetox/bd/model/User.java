package com.projetox.bd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="username")
	@Expose
	private String username;
	
	@Column(name="facebook_id",unique=true)
	@Expose
	private String facebookId;
	
	@Column(name="name")
	@Expose
	private String name;
	
	@Column(name="gender")
	@Expose
	private String gender;

	public User() {}
	
	public User(String facebookId, String name, String username, String gender) {
		this.facebookId = facebookId;
		this.name = name;
		this.username = username;
		this.gender = gender;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
