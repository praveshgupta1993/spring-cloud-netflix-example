package com.nagarro.nagp.communication.event;

import java.io.Serializable;

public class A1Event implements Serializable {

	private static final long serialVersionUID = 7940212738337520692L;
	private String id;
	private Integer age;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAge() {
		return age;
	}

}
