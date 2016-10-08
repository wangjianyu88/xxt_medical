package com.medical.register.service.impl;
import java.util.Date;

import com.medical.family.dao.FamilyDao;
import com.medical.family.entity.FamilyUser;
import com.medical.login.entity.Account;
import com.medical.register.service.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medical.register.bean.UserRegisterBean;
import com.medical.register.dao.RegisterDao;
import com.medical.login.dao.LoginDao;
import com.medical.register.entity.AccountRegister;
@Service
public class RegisterServiceImpl implements RegisterService{
	@Autowired
	private RegisterDao registerDao;
	@Autowired
	private LoginDao loginDao;
	@Autowired
	private FamilyDao familyDao;
	@Override
	public String registerUser(String userId){
		return registerDao.registerAccount(userId);
	}
	@Override
	@Transactional
	public AccountRegister saveUserRegister(UserRegisterBean userRegisterBean) {
		AccountRegister userRegister =new AccountRegister();
		userRegister.setUsername(userRegisterBean.getUsername());
		userRegister.setPassword(userRegisterBean.getPassword());
		userRegister.setAge(userRegisterBean.getAge());
		userRegister.setMobile(userRegisterBean.getMobile());
		userRegister.setName(userRegisterBean.getName());
		userRegister.setSex(userRegisterBean.getSex());
		userRegister.setEmail(userRegisterBean.getEmail());
		userRegister.setStatus(userRegisterBean.getStatus());
		userRegister.setLastUpdatetime((new Date()).getTime());
		registerDao.saveAccountRegister(userRegister);
		return userRegister;
	}
	@Override
	public Account getAccount(String username, String email){
		return registerDao.getAccount(username, email);
	}
	@Override
	public Account getAccountByPassword(String username, String password){
		return registerDao.getAccountByPassword(username, password);
	}
	@Override
	@Transactional
	public void updateAccount(Account account){
		registerDao.updateAccount(account);
	}
	@Override
	@Transactional
	public void deleteAccount(Account account){
		registerDao.deleteAccount(account);
	}
	@Override
	public boolean checkUsernameValid(String username){
		int cnn = registerDao.getUsernameCountForAccount(username);
		if(cnn > 0){
			return false;
		}
		AccountRegister accountRegister = registerDao.getAccountRegisterByUsername(username, 24);
		if(accountRegister != null) {
			return false;
		}
		return true;
	}
	@Override
	public AccountRegister getAccountRegisterById(String accountId, int hour){
		return registerDao.getAccountRegisterById(accountId, hour);
	}
	@Override
	public AccountRegister getAccountRegisterByUsername(String username,int hour){
		return registerDao.getAccountRegisterByUsername(username, hour);
	}
	@Override
	@Transactional
	public void activateRegister(AccountRegister accountRegister){
		long lastUpdateTime = new Date().getTime();
		//生成该用户
		Account account = new Account();
		account.setAccountId(accountRegister.getAccountId());
		account.setAge(accountRegister.getAge());
		account.setEmail(accountRegister.getEmail());
		account.setLastUpdatetime(lastUpdateTime);
		account.setMobile(accountRegister.getMobile());
		account.setName(accountRegister.getName());
		account.setPassword(accountRegister.getPassword());
		account.setSex(accountRegister.getSex());
		account.setUsername(accountRegister.getUsername());
		loginDao.saveAccount(account);
		//设置该用户的注册信息为激活状态
		accountRegister.setStatus("1");
		accountRegister.setLastUpdatetime(lastUpdateTime);
		registerDao.updateAccountRegister(accountRegister);
		//生成当前默认用户
		FamilyUser familyUser = new FamilyUser();
		familyUser.setAccountId(account.getAccountId());
		familyUser.setLastUpdateTime(lastUpdateTime);
		familyUser.setName(account.getName());
		familyUser.setStatus(2);
		familyUser.setType(1);
		familyDao.saveFamilyUser(familyUser);
	}
	@Override
	@Transactional
	public void updateAccountRegister(AccountRegister accountRegister){
		registerDao.updateAccountRegister(accountRegister);
	}
	@Override
	@Transactional
	public void deleteAccountRegister(AccountRegister accountRegister){
		registerDao.deleteAccountRegister(accountRegister);
	}
}
