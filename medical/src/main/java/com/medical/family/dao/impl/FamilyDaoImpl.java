package com.medical.family.dao.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.medical.common.basedao.BaseDao;
import com.medical.common.util.Page;
import com.medical.family.dao.FamilyDao;
import com.medical.family.entity.FamilyUser;
@Repository
public class FamilyDaoImpl implements FamilyDao{
	@Autowired
    private BaseDao  baseDao;
	@Override
	public void saveFamilyUser(FamilyUser familyUser){
		baseDao.create(familyUser);
	}
	@Override
	public void updateFamilyUser(FamilyUser familyUser){
		baseDao.update(familyUser);
		
	}
	@Override
	public FamilyUser getFamilyUser(String userId){
		String select = " from FamilyUser where userId = ?";
		Object[] values = {userId};
		return (FamilyUser)baseDao.getObjectByHQL(select, values);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<FamilyUser> queryPageFamilyUsersByAccount(String accountId, long startTime, long endTime, Page page){
		String selectCount = "select count(userId) from FamilyUser where  accountId = ? and lastUpdateTime > ? and lastUpdateTime <= ? ";
		String select = " from FamilyUser  where accountId = ? and lastUpdateTime > ? and lastUpdateTime <= ? ";
		Object[] values = {accountId, startTime, endTime};
		return (List<FamilyUser>)baseDao.getListByHQL(selectCount, select, values, page);
	}
	@Override
	public long queryFamilyUserCount(long startTime, long endTime){
		String selectCount = "select count(userId) from FamilyUser where lastUpdateTime > ? and lastUpdateTime <= ? ";
		Object[] values = {startTime, endTime};
		return (Long)baseDao.getObjectByHQL(selectCount, values);
	}
	@Override
	public FamilyUser getActivityFamilyUser(String accountId){
		String select = " from FamilyUser where accountId = ? and status = 2";
		Object[] values = {accountId};
		return (FamilyUser)baseDao.getObjectByHQL(select, values);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<FamilyUser> queryFamilyUsers(String accountId){
		String select = " from FamilyUser where accountId = ? and status !=0 order by status desc, lastUpdateTime";
		Object[] values = {accountId};
		return (List<FamilyUser>)baseDao.getListByHQL(select, values);
	}
}
