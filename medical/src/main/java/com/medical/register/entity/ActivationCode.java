package com.medical.register.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 激活码
 * @author 
 * @time 5:15:37 PM
 */
@Entity
@Table(name="t_md_activation_code")
public class ActivationCode {
	/*验证码*/
	private String code;
	/*移动设备标识*/
	private String imei;
	/*开始时间*/
	private long beginDate;
	/*结束时间*/
	private long endDate;
	/*状态，1：激活，0:禁用*/
	private int state;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "code", length = 128)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(name="imei",length=128,nullable=true)
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	@Column(name="begin_date",length=19,nullable=true)
	public long getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(long beginDate) {
		this.beginDate = beginDate;
	}
	@Column(name="end_date",length=19,nullable=true)
	public long getEndDate() {
		return endDate;
	}
	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
	@Column(name="state",length=2,nullable=true)
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

}
