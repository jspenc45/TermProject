package com.example.termproject;

import org.json.JSONException;
import org.json.JSONObject;

public class Account {
	public String name;
	private String password;
	public String emailAddress;
	
	public Account(String name, String emailAddress, String password) {
		super();
		this.name = name;
		this.password = password;
		this.emailAddress = emailAddress;
	}
	public Account(JSONObject jsonObject) throws JSONException {
		if (jsonObject.has("Username__c"))
			this.name = jsonObject.getString("Username__c");
		if (jsonObject.has("password__c"))
			this.password = jsonObject.getString("password__c");
		if (jsonObject.has("email__c"))
			this.emailAddress = jsonObject.getString("email__c");
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	protected boolean equalsPassword(String other) {
		return other.equals(this.password);
	}
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("Username__c", name);
		json.put("email__c", emailAddress);
		json.put("password__c", password);
		return json;
	}
}
