package com.orwlw.model;

import java.io.Serializable;

public class CheckResultItem implements Serializable {

	/**
	 * 结果实体
	 */
	private static final long serialVersionUID = -5038934676915852403L;

	private String result;
	private String comm;
	private String image;
	private String category;
	private boolean ischeck;
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getComm() {
		return comm;
	}
	public void setComm(String comm) {
		this.comm = comm;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public boolean getcheck() {
		return ischeck;
	}
	public void setcheck(boolean ischeck) {
		this.ischeck = ischeck;
	}
	
	
}
