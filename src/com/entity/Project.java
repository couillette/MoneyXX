package com.entity;

import com.stackmob.sdk.model.StackMobModel;

public class Project extends StackMobModel {

	private String projectTitle;
	private String phone;
	private String email;
	private ProjectAccount project_account;

	
	
	public Project(String title, String email, String phone) {
		super(Project.class);
		this.projectTitle = title;
		this.phone = phone;
		this.email = email;
	}

	public Project(String title, String email) {
		super(Project.class);
		this.projectTitle = title;
		this.email = email;
	}
	
	
	
	public Project() {
		super(Project.class);
	}
	public ProjectAccount getProject_account() {
		return project_account;
	}
	public void setProject_account(ProjectAccount project_account) {
		this.project_account = project_account;
	}
	public String getprojectTitle() {
		return projectTitle;
	}
	public void setprojectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}

