package com.parlour.business.model;

import org.json.JSONObject;

/**
 * Member model class
 * @author DEB
 */
public class Member {
		
	private String email;
	private String password;
	private String fullName;
	private String phoneNumber;
	
	public Member(){
		
	}
	
	public Member(JSONObject jsonObject) {
		
	}
	
	public Member(String email, String password, String fullName, String phoneNumber) {
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		
	}
	
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getFullName() {
		return fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
}
