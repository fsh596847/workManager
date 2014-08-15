package com.orwlw.model;

import java.io.Serializable;

public class ResultComm implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String category;
	private String commCode;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCommCode() {
		return commCode;
	}
	public void setCommCode(String commCode) {
		this.commCode = commCode;
	}
	
	
}
