package com.orwlw.net;

import java.util.List;

import com.orwlw.model.DataBean;
import com.orwlw.model.DataBeanToo;
import com.orwlw.model.ItemNumber;
import com.orwlw.model.Radio_Statue;

public class Image_Comm_Satue {
	private List<String> list_comm;// 商品
	private List<DataBeanToo> list_image;
	private List<Radio_Statue> list_radio_statue;
	private List<List<ItemNumber>> num_list;//数字题图片
	private List<DataBean> list_num;//数字题内容
	public Image_Comm_Satue() {
		super();
	}
	public List<List<ItemNumber>> getNum_list() {
		return num_list;
	}
	public void setNum_list(List<List<ItemNumber>> num_list) {
		this.num_list = num_list;
	}



	public List<DataBean> getList_num() {
		return list_num;
	}



	public void setList_num(List<DataBean> list_num) {
		this.list_num = list_num;
	}



	public Image_Comm_Satue(List<String> list_comm, List<DataBeanToo> list_image,
			List<Radio_Statue> list_radio_statue) {
		super();
		this.list_comm = list_comm;
		this.list_image = list_image;
		this.list_radio_statue = list_radio_statue;
	}

	public Image_Comm_Satue(List<String> list_comm, List<DataBeanToo> list_image) {
		super();
		this.list_comm = list_comm;
		this.list_image = list_image;
	}

	public List<String> getList_comm() {
		return list_comm;
	}

	public void setList_comm(List<String> list_comm) {
		this.list_comm = list_comm;
	}

	public List<DataBeanToo> getList_image() {
		return list_image;
	}

	public void setList_image(List<DataBeanToo> list_image) {
		this.list_image = list_image;
	}

	public List<Radio_Statue> getList_radio_statue() {
		return list_radio_statue;
	}

	public void setList_radio_statue(List<Radio_Statue> list_radio_statue) {
		this.list_radio_statue = list_radio_statue;
	}
    
}
