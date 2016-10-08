package com.medical.register.dao.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.math.BigInteger;

import com.medical.login.entity.Account;
import com.medical.register.dao.RegisterDao;
import com.medical.register.entity.AccountRegister;
import com.medical.common.basedao.BaseDao;
@Repository
public class RegisterDaoImpl implements RegisterDao{
	@Autowired
    private BaseDao  baseDao;
	@Override
	public String registerAccount(String userId){
		return (String)baseDao.getObjectBySQL("select name from t_md_account where account_id = ?", new String[]{userId});
	}
	@Override 
	public int getUsernameCountForAccount(String username){
		BigInteger count = (BigInteger)baseDao.getObjectBySQL("select count(account_id) from t_md_account where username=?", new String[]{username});
		return count.intValue();
	}
	@Override 
	public Account getAccount(String username, String email){
		String select = "from Account where username=? and email = ?";
		Object[] values = {username, email};
		return (Account)baseDao.getObjectByHQL(select, values);
	}
	@Override 
	public Account getAccountByPassword(String username, String password){
		String select = "from Account where username=? and password = ?";
		Object[] values = {username, password};
		return (Account)baseDao.getObjectByHQL(select, values);
	}
	@Override 
	public void updateAccount(Account account){
		baseDao.update(account);
	}
	@Override 
	public void deleteAccount(Account account){
		baseDao.delete(account);
	}
	@Override
	public AccountRegister getAccountRegisterById(String accountId, int hour){
		long millisecond = new Date().getTime() - hour * 3600l * 1000l; 
		String sql = "from AccountRegister where account_id = ? and last_update_time > ? ";
		Object[] params = {accountId, millisecond};
		return (AccountRegister)baseDao.getObjectByHQL(sql, params);
	}
	@Override
	public AccountRegister getAccountRegisterByUsername(String username, int hour){
		long millisecond = new Date().getTime() - hour * 3600l * 1000l; 
		String sql = "from AccountRegister where username=? and last_update_time>? ";
		Object[] params = {username, millisecond};
		return (AccountRegister)baseDao.getObjectByHQL(sql, params);
	}
	@Override
	public void saveAccountRegister(AccountRegister accountRegister) {
		baseDao.create(accountRegister);
	}
	@Override
	public void updateAccountRegister(AccountRegister accountRegister){
		baseDao.update(accountRegister);
	}
	@Override
	public void deleteAccountRegister(AccountRegister accountRegister) {
		baseDao.delete(accountRegister);
	}
}
