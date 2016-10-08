package com.medical.login.dao;

import com.medical.login.entity.Account;

public interface LoginDao {
	/**
	 * 根据用户名和密码获取用户
	 * @param username
	 * @param password
	 * @return
	 */
	Account getAccount(String username, String password);
	/**
	 * 插入用户
	 * @param user
	 * @return
	 */
	void saveAccount(Account account);
}
