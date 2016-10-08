package com.medical.register.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medical.register.dao.ActivationDao;
import com.medical.register.entity.ActivationCode;
import com.medical.register.service.ActivationService;
@Service
public class ActivationServiceImpl implements ActivationService{
	@Autowired
	private ActivationDao activationDao;
	@Override
	public ActivationCode getActivationCode(String code){
		return activationDao.getActivationCode(code);
	}
	@Override
	@Transactional
	public void updateActivationCode(ActivationCode activationCode){
		activationDao.updateActivationCode(activationCode);
	}

}
