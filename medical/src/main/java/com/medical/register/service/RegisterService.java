package com.medical.register.service;

import com.medical.login.entity.Account;
import com.medical.register.bean.UserRegisterBean;
import com.medical.register.entity.AccountRegister;

public interface RegisterService {
	/**
	 * 注册用户
	 * @param userId
	 * @return
	 */
	String registerUser(String userId);
	/**
	 * 保存注册信息
	 * @param registerUserBean
	 */
	AccountRegister saveUserRegister(UserRegisterBean registerUserBean);
	/**
	 * 校验用户名的合法性：是否已被注册过
	 * @return
	 */
	boolean checkUsernameValid(String username);
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
	 * 根据用户id获取指定时间内，曾经注册的用户
	 * @param getAccountRegisterById
	 * @param hour
	 * @return
	 */
	AccountRegister getAccountRegisterById(String accountId, int hour);
	/**
	 * 根据用户名获取指定时间内，曾经的注册用户
	 * @param username
	 * @param hour
	 * @return
	 */
	AccountRegister getAccountRegisterByUsername(String username,int hour);
	/**
	 * 激活用户
	 * @param userRegister
	 * @return
	 */
	void activateRegister(AccountRegister accountRegister);
	/**
	 * 更新
	 * @param accountRegister
	 */
	void updateAccountRegister(AccountRegister accountRegister);
	/**
	 * 删除注册信息
	 * @param accountRegister
	 */
	void deleteAccountRegister(AccountRegister accountRegister);

}
