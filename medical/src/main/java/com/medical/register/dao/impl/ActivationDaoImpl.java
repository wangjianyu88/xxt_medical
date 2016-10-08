package com.medical.register.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.medical.common.basedao.BaseDao;
import com.medical.login.entity.Account;
import com.medical.register.dao.ActivationDao;
import com.medical.register.entity.ActivationCode;
@Repository
public class ActivationDaoImpl implements ActivationDao{
	@Autowired
    private BaseDao  baseDao;
	@Override 
	public ActivationCode getActivationCode(String code){
		String select = "from ActivationCode where code=? ";
		Object[] values = {code};
		return (ActivationCode)baseDao.getObjectByHQL(select, values);
	}
	
	@Override 
	public void updateActivationCode(ActivationCode activationCode){
		baseDao.update(activationCode);
	}

}
