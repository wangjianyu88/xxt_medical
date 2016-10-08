package com.medical.synchrodata.bean;

public class Result {
	/**
	 * 操作结果
	 */
	private String resultStatus;
	/**
	 * 提示信息
	 */
	private String resultInfo;
	/**
	 * 操作成功
	 */
	public static String SYN_SUCCESS = "1";
	/**
	 * 操作失败
	 */
	public static String SYN_FAIL = "0" ;
	/**
	 * 构造函数
	 */
	public Result(String resultStatus, String resultInfo){
		this.resultStatus = resultStatus;
		this.resultInfo = resultInfo;
	}
	
	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public String getResultInfo() {
		return resultInfo;
	}
	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

	
}
