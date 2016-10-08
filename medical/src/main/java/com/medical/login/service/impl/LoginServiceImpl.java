package com.medical.login.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medical.login.service.LoginService;
import com.medical.login.dao.LoginDao;
import com.medical.login.entity.Account;
@Service
public class LoginServiceImpl implements LoginService{
	@Autowired
	private LoginDao loginDao;
	@Override
	public Account login(String username, String password){
		return loginDao.getAccount(username, password);
	}
	@Override
	@Transactional
	public void saveAccount(Account account){
		loginDao.saveAccount(account);
	}

}
