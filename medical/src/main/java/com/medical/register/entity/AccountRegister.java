package com.medical.register.entity;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
/**
 * 用户注册
 * @author 
 * @time 下午4:36:52
 */
@Entity
@Table(name="t_md_account_register")
public class AccountRegister {
	/*人员id*/
	private String accountId;
	/*用户名*/
	private String username;
	/*密码*/
	private String password;
	/*人员名*/
	private String name;
	/*人员手机号*/
	private String mobile;
	/*性别*/
	private String sex;
	/*年龄*/
	private Integer age;
	/*邮箱*/
	private String email;
	/*状态，0:未激活，1：激活*/
	private String status = "0";
	/*最后更新时间*/
	private long lastUpdatetime;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "account_id", length = 32)
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	@Column(name="name",length=32,nullable=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="mobile",length=16,nullable=true)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(name="sex",length=8,nullable=true)
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Column(name="age",length=16,nullable=true)
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@Column(name="last_update_time",length=19,nullable=true)
	public long getLastUpdatetime() {
		return lastUpdatetime;
	}
	public void setLastUpdatetime(long lastUpdatetime) {
		this.lastUpdatetime = lastUpdatetime;
	}
	@Column(name="username",length=32,nullable=true)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column(name="password",length=32,nullable=true)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="email",length=64,nullable=true)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="status",length=2,nullable=true)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
