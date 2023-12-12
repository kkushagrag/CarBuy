package com.model;

public class Manager {
	private int manager_id;
	private String manager_name;
	private String manager_email;
	private String manager_password;

	public Manager(int manager_id, String manager_name, String manager_email, String manager_password) {
		this.manager_id = manager_id;
		this.manager_name = manager_name;
		this.manager_email = manager_email;
		this.manager_password = manager_password;
	}

	public String getManager_name() {
		return manager_name;
	}
	
	@Override
	public String toString() {
		return "Your manager Details:\n[ID: " + manager_id +"]\nName: " + manager_name + "\nEmail: " + manager_email
				+ "\nPassword " + manager_password+"\n";
	}
}
