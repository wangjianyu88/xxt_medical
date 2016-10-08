package com.medical.maintain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 吃药时间表
 * @author 
 * @time 上午11:47:49
 */
@Entity
@Table(name="t_md_medical_time")
public class MedicalTime {
	/*服务器端id*/
	private String medicalTimeId;
	/*客户端id*/
	private String clientMedicalTimeId;
	/*外键：药物表主键*/
	private String medicalId;
	/*第几次服药*/
	private Integer whichTimes;
	/*服药时间点*/
	private String takeTime;
	/*状态*/
	private int status;
	/*创建时间*/
	private long createDate = new Date().getTime();
	/*修改时间*/
	private long modifyDate = new Date().getTime();
	/*最后更新时间*/
	private long lastUpdateTime ;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "medical_time_id", length = 32)
	public String getMedicalTimeId() {
		return medicalTimeId;
	}
	public void setMedicalTimeId(String medicalTimeId) {
		this.medicalTimeId = medicalTimeId;
	}
	@Column(name="medical_id",nullable=false)
	public String getMedicalId() {
		return medicalId;
	}
	public void setMedicalId(String medicalId) {
		this.medicalId = medicalId;
	}
	@Column(name="which_times",nullable=true)
	public Integer getWhichTimes() {
		return whichTimes;
	}
	public void setWhichTimes(Integer whichTimes) {
		this.whichTimes = whichTimes;
	}
	@Column(name="take_time",nullable=true)
	public String getTakeTime() {
		return takeTime;
	}
	public void setTakeTime(String takeTime) {
		this.takeTime = takeTime;
	}
	@Column(name="status",nullable=false)
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Column(name="create_date",length=19,nullable=false)
	public long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}
	@Column(name="modify_date",length=19,nullable=false)
	public long getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(long modifyDate) {
		this.modifyDate = modifyDate;
	}
	@Column(name="last_update_time",nullable=false)
	public long getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	@Transient
	public String getClientMedicalTimeId() {
		return clientMedicalTimeId;
	}
	public void setClientMedicalTimeId(String clientMedicalTimeId) {
		this.clientMedicalTimeId = clientMedicalTimeId;
	}

}
