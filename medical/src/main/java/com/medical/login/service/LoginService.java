package com.medical.login.service;
import com.medical.login.entity.Account;
public interface LoginService {
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	Account login(String username, String password);
	/**
	 * 插入用户
	 * @param user
	 * @return
	 */
	void saveAccount(Account account);

}
