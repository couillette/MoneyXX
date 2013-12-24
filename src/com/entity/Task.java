package com.entity;

import java.util.Date;

import com.stackmob.sdk.model.StackMobModel;

public class Task extends StackMobModel {
	private String name;
	private Date dd;

	public Task(String name, Date dd) {
		super(Task.class);
		this.name = name;
		this.dd = dd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDd() {
		return dd;
	}

	public void setDd(Date dd) {
		this.dd = dd;
	}

}
