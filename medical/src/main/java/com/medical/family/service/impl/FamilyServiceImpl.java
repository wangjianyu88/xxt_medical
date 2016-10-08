package com.medical.family.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medical.common.util.Page;
import com.medical.family.entity.FamilyUser;
import com.medical.family.service.FamilyService;
import com.medical.family.dao.FamilyDao;
@Service
public class FamilyServiceImpl implements FamilyService{
	@Autowired
	private FamilyDao familyDao;
	@Override
	@Transactional
	public void saveFamilyUser(FamilyUser familyUser){
		familyDao.saveFamilyUser(familyUser);
	}
	@Override
	@Transactional
	public void updateFamilyUser(FamilyUser familyUser){
		familyDao.updateFamilyUser(familyUser);
	}
	@Override
	public FamilyUser getFamilyUser(String userId){
		return familyDao.getFamilyUser(userId);
	}
	@Override
	public List<FamilyUser> queryPageFamilyUsersByAccount(String accountId, long startTime, long endTime, Page page){
		return familyDao.queryPageFamilyUsersByAccount(accountId, startTime, endTime, page);
	}
	@Override
	public long queryFamilyUserCount(long startTime, long endTime){
		return familyDao.queryFamilyUserCount(startTime, endTime);
	}
	@Override
	public FamilyUser getActivityFamilyUser(String accountId){
		return familyDao.getActivityFamilyUser(accountId);
	}
	@Override
	public List<FamilyUser> queryFamilyUsers(String accountId){
		return familyDao.queryFamilyUsers(accountId);
	}

}
