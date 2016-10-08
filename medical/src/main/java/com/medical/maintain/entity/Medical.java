package com.medical.maintain.entity;
import javax.persistence.Transient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import org.hibernate.annotations.GenericGenerator;
/**
 * 药物表
 * @author 
 * @time 上午11:47:49
 */
@Entity
@Table(name="t_md_medical")
public class Medical {
	/*服务端药物id*/
	private String medicalId;
	/*客户端药物记录*/
	private String clientMedicalId;
	/*药物名*/
	private String name;
	/*用户id*/
	private String userId;
	/*开始日期*/
	private Date startDate;
	/*结束日期*/
	private Date endDate;
	/*将天数*/
	private Integer splitDays;
	/*持续时间*/
	private Integer days;
	/*服用数量*/
	private Integer takeNum;
	/*服用单位*/
	private Integer takeUnit;
	/*吃药时间*/
	private Integer eatTime;
	/*备注*/
	private String remark;
	/*快捷设置的服用时间段*/
	private int takeFrequency;
	/*吃药时间*/
	private String takeTime;
	/*状态*/
	private Integer status = 1;
	/*创建时间*/
	private long createDate = new Date().getTime();
	/*修改时间*/
	private long modifyDate = new Date().getTime();
	/*更新时间*/
	private long lastUpdateTime ;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "medical_id", length = 32)
	public String getMedicalId() {
		return medicalId;
	}
	public void setMedicalId(String medicalId) {
		this.medicalId = medicalId;
	}
	@Column(name="name",length=32,nullable=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="user_id",length=64,nullable=true)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(name="start_date",nullable=true)
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@Column(name="end_date",nullable=true)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Column(name="split_days",length=3,nullable=true)
	public Integer getSplitDays() {
		return splitDays;
	}
	public void setSplitDays(Integer splitDays) {
		this.splitDays = splitDays;
	}
	@Column(name="days",length=3,nullable=true)
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	@Column(name="take_num",length=3,nullable=true)
	public Integer getTakeNum() {
		return takeNum;
	}
	public void setTakeNum(Integer takeNum) {
		this.takeNum = takeNum;
	}
	@Transient
	public String getClientMedicalId() {
		return clientMedicalId;
	}
	public void setClientMedicalId(String clientMedicalId) {
		this.clientMedicalId = clientMedicalId;
	}
	@Column(name="take_unit",length=3,nullable=true)
	public Integer getTakeUnit() {
		return takeUnit;
	}
	public void setTakeUnit(Integer takeUnit) {
		this.takeUnit = takeUnit;
	}
	@Column(name="eat_time",length=1,nullable=true)
	public Integer getEatTime() {
		return eatTime;
	}
	public void setEatTime(Integer eatTime) {
		this.eatTime = eatTime;
	}
	@Column(name="remark",length=128,nullable=true)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="take_frequency",nullable=true)
	public int getTakeFrequency() {
		return takeFrequency;
	}
	public void setTakeFrequency(int takeFrequency) {
		this.takeFrequency = takeFrequency;
	}
	@Column(name="take_time",length=1024,nullable=true)
	public String getTakeTime() {
		return takeTime;
	}
	public void setTakeTime(String takeTime) {
		this.takeTime = takeTime;
	}
	@Column(name="status",length=1,nullable=true)
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
	@Column(name="last_update_time",length=19,nullable=false)
	public long getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	

}
