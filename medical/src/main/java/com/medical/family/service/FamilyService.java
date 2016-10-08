package com.medical.family.service;

import java.util.List;

import com.medical.common.util.Page;
import com.medical.family.entity.FamilyUser;

public interface FamilyService {
	/**
	 * 保存家庭成员
	 * @param familyUser
	 */
	public void saveFamilyUser(FamilyUser familyUser);
	/**
	 * 修改家庭成员
	 * @param familyUser
	 */
	public void updateFamilyUser(FamilyUser familyUser);
	/**
	 * 查询家庭成员
	 * @param userId
	 * @return
	 */
	public FamilyUser getFamilyUser(String userId);
	/**
	 * 分页查询家庭成员
	 * @param accountId
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	public List<FamilyUser> queryPageFamilyUsersByAccount(String accountId, long startTime, long endTime, Page page);
	/**
	 * 获取家庭成员总数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public long queryFamilyUserCount(long startTime, long endTime);
	/**
	 * 获取账号的当前用户
	 * @param accountId
	 * @return
	 */
	public FamilyUser getActivityFamilyUser(String accountId);
	/**
	 * 获取账号所有用户
	 * @param accountId
	 * @return
	 */
	public List<FamilyUser> queryFamilyUsers(String accountId);
	

}
