package com.medical.login.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.medical.common.basedao.BaseDao;
import com.medical.login.dao.LoginDao;
import com.medical.login.entity.Account;
@Repository
public class LoginDaoImpl implements LoginDao{
	@Autowired
    private BaseDao  baseDao;
	@Override
	public Account getAccount(String username, String password){
		String sql = "from Account where username = ? and password = ?";
		Object[] params = {username,password};
		return (Account)baseDao.getObjectByHQL(sql, params);
	}
	@Override
	public void saveAccount(Account account){
		baseDao.create(account);
	}

}
