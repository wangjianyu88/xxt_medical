package com.medical.family.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 家庭成员
 * @author 
 * @time 下午11:03:16
 */
@Entity
@Table(name="t_md_family_user")
public class FamilyUser {
	/*服务器端成员id*/
	private String userId;
	/*客户端成员id*/
	private String clientUserId;
	/*账号*/
	private String accountId;
	/*成员名*/
	private String name;
	/*类型*/
	private int type;
	/*状态,0:禁用，1：激活，2：激活且当前身份*/
	private int status;
	/*创建时间*/
	private long createDate = new Date().getTime();
	/*修改时间*/
	private long modifyDate = new Date().getTime();
	/*同步时间*/
	private long lastUpdateTime;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "user_id", length = 32)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Transient
	public String getClientUserId() {
		return clientUserId;
	}
	public void setClientUserId(String clientUserId) {
		this.clientUserId = clientUserId;
	}
	@Column(name="account_id",nullable=false)
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	@Column(name="name",nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="type",nullable=false)
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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

}
