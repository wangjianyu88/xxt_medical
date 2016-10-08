package com.medical.register.dao;
import com.medical.login.entity.Account;
import com.medical.register.entity.AccountRegister;
public interface RegisterDao {
	/**
	 * 注册账号
	 * @param userId 用户id
	 * @return
	 */
	String registerAccount(String userId);
	/**
	 * 已激活的用户名的个数
	 * @param username
	 * @return
	 */
	int getUsernameCountForAccount(String username);
	/**
	 * 根据用户名和邮箱获取账号
	 * @param username
	 * @param  email
	 * @return
	 */
	Account getAccount(String username, String email);
	/**
	 * 根据用户名和密码获取账号
	 * @param username
	 * @param password
	 * @return
	 */
	Account getAccountByPassword(String username, String password);
	/**
	 * 修改账号
	 * @param account
	 */
	void updateAccount(Account account);
	/**
	 * 删除账号
	 * @param account
	 */
	void deleteAccount(Account account);
	/**
	 * 获取指定时间内，曾经注册的用户
	 * @param accountId
	 * @param hour
	 * @return
	 */
	AccountRegister getAccountRegisterById(String accountId, int hour);
	/**
	 * 在指定时间内注册成功过的用户中，使用过username用户名的个数
	 * @param username
	 * @param hour
	 * @return
	 */
	AccountRegister getAccountRegisterByUsername(String username,int hour);
	/**
	 * 新增或修改用户
	 * @param user
	 */
	void saveAccountRegister(AccountRegister accountRegister);
	/**
	 * 更新用户
	 * @param userRegister
	 */
	void updateAccountRegister(AccountRegister accountRegister);
	/**
	 * 删除注册信息
	 * @param accountRegister
	 */
	void deleteAccountRegister(AccountRegister accountRegister);

}
