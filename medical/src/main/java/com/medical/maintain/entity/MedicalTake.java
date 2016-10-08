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
 * 药物发用记录表
 * @author 
 * @time 下午11:03:16
 */
@Entity
@Table(name="t_md_medical_take")
public class MedicalTake {
	/*服务器端id*/
	private String medicalTakeId;
	/*客户端id*/
	private String clientMedicalTakeId;
	/*外键药物id*/
	private String medicalId;
	/*药物名称*/
	private String medicalName;
	/*服用日期*/
	private Date takeDate;
	/*外键*/
	private String medicalTimeId;
	/*设定的服用时间*/
	private String setTakeTime;
	/*设定的服用数量*/
	private Integer setTakeNum;
	/*t_medical表中take_unit*/
	private Integer setTakeUnit;
	/*服用类型，0 服用，1 错过 ，2 跳过*/
	private Integer takeType;
	/*实际服用时间*/
	private String realTakeTime;
	/*实际服用单位*/
	private Integer realTakeNum;
	/*备注*/
	private String remark;
	/*状态*/
	private Integer status;
	/*创建时间*/
	private long createDate = new Date().getTime();
	/*修改时间*/
	private long modifyDate = new Date().getTime();
	/*最后更新时间*/
	private long lastUpdateTime;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "medical_take_id", length = 32)
	public String getMedicalTakeId() {
		return medicalTakeId;
	}
	public void setMedicalTakeId(String medicalTakeId) {
		this.medicalTakeId = medicalTakeId;
	}
	@Transient
	public String getClientMedicalTakeId() {
		return clientMedicalTakeId;
	}
	public void setClientMedicalTakeId(String clientMedicalTakeId) {
		this.clientMedicalTakeId = clientMedicalTakeId;
	}
	@Column(name="medical_id",nullable=false)
	public String getMedicalId() {
		return medicalId;
	}
	public void setMedicalId(String medicalId) {
		this.medicalId = medicalId;
	}
	@Column(name="take_date",nullable=true)
	public Date getTakeDate() {
		return takeDate;
	}
	public void setTakeDate(Date takeDate) {
		this.takeDate = takeDate;
	}
	@Column(name="set_take_time",nullable=true)
	public String getSetTakeTime() {
		return setTakeTime;
	}
	public void setSetTakeTime(String setTakeTime) {
		this.setTakeTime = setTakeTime;
	}
	@Column(name="set_take_num",nullable=true)
	public Integer getSetTakeNum() {
		return setTakeNum;
	}
	public void setSetTakeNum(Integer setTakeNum) {
		this.setTakeNum = setTakeNum;
	}
	@Column(name="take_type",nullable=true)
	public Integer getTakeType() {
		return takeType;
	}
	public void setTakeType(Integer takeType) {
		this.takeType = takeType;
	}
	@Column(name="real_take_time",nullable=true)
	public String getRealTakeTime() {
		return realTakeTime;
	}
	public void setRealTakeTime(String realTakeTime) {
		this.realTakeTime = realTakeTime;
	}
	@Column(name="remark",nullable=true)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="status",nullable=false)
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
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
	@Column(name="medical_name",nullable=true)
	public String getMedicalName() {
		return medicalName;
	}
	public void setMedicalName(String medicalName) {
		this.medicalName = medicalName;
	}
	@Column(name="medical_time_id",nullable=true)
	public String getMedicalTimeId() {
		return medicalTimeId;
	}
	public void setMedicalTimeId(String medicalTimeId) {
		this.medicalTimeId = medicalTimeId;
	}
	@Column(name="set_take_unit",nullable=true)
	public Integer getSetTakeUnit() {
		return setTakeUnit;
	}
	public void setSetTakeUnit(Integer setTakeUnit) {
		this.setTakeUnit = setTakeUnit;
	}
	@Column(name="real_take_num",nullable=true)
	public Integer getRealTakeNum() {
		return realTakeNum;
	}
	public void setRealTakeNum(Integer realTakeNum) {
		this.realTakeNum = realTakeNum;
	}
	

}
