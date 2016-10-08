package com.medical.synchrodata.bean;

import java.util.Date;

/**
 * 同步下载数据请求头
 * @author 
 * @time 上午9:19:10
 */
public class SynDownloadRequest {
	/*账号*/
	private String accountId;
	/*开始时间*/
	private long startTime;
	/*结束时间*/
	private long endTime = new Date().getTime();
	/*当前页*/
	private int page;
	/*页码数*/
	private int pageSize;
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
