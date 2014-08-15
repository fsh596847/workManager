package com.orwlw.model;

import java.io.Serializable;
import java.util.List;

public class CheckResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5038934676915852403L;

	
	private String brandId;
	
	private List<CheckResultItem> selectResult;
	private List<CheckResultItem> numResult;
	private List<CheckResultItem> textResult;
	private List<String> images;
	private List<String> comms;
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public List<CheckResultItem> getSelectResult() {
		return selectResult;
	}
	public void setSelectResult(List<CheckResultItem> selectResult) {
		this.selectResult = selectResult;
	}
	public List<CheckResultItem> getNumResult() {
		return numResult;
	}
	public void setNumResult(List<CheckResultItem> numResult) {
		this.numResult = numResult;
	}
	public List<CheckResultItem> getTextResult() {
		return textResult;
	}
	public void setTextResult(List<CheckResultItem> textResult) {
		this.textResult = textResult;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public List<String> getComms() {
		return comms;
	}
	public void setComms(List<String> comms) {
		this.comms = comms;
	}
	
	
	
}
