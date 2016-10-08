package com.medical.common.util;

public class SysException extends Exception {
	private static final long serialVersionUID = 2295129033357234705L;

	/**
	 * 错误代码
	 */
	private String code = "0";

	/**
	 * 错误信息
	 */
	private String msg = "请求失败";

	/**
	 * 异常信息
	 */
	private Exception e;

	public SysException(String error) {
		//super(error);
		this.msg = error;
	}
	public SysException( String code, String msg) {

		this.code = code;
		this.msg = msg;
	}

	public SysException(Exception e, String code, String msg) {

		this.code = code;
		this.msg = msg;
		this.setE(e);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Exception getE() {
		return e;
	}

	public void setE(Exception e) {
		this.e = e;
	}
}
