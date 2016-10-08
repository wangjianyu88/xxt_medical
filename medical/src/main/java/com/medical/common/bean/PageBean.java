package com.medical.common.bean;

import java.util.List;

import com.medical.common.util.Page;
/**
 * 分页返回结果
 * @author 
 * @time 上午11:05:50
 * @param <T>
 */
public class PageBean<T> extends Page {
	private static final long serialVersionUID = 0l;
	private List<T> itemList;

	public List<T> getItemList() {
		return itemList;
	}

	public void setItemList(List<T> itemList) {
		this.itemList = itemList;
	}
}
