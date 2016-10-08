package com.medical.maintain.service;
import java.util.List;

import com.medical.common.util.Page;
import com.medical.maintain.entity.Medical;
import com.medical.maintain.entity.MedicalTake;
import com.medical.maintain.entity.MedicalTime;
public interface MedicalService {
	/**
	 * 获取药物
	 * @param medicalId
	 * @return
	 */
	public Medical getMedicalById(String medicalId);
	/**
	 * 保存药物
	 * @param medical
	 */
	public void saveMedical(Medical medical);
	/**
	 * 保存药物
	 * @param medical
	 */
	public void saveMedical(Medical medical, List<MedicalTime> medicalTimeList);
	/**
	 * 修改药物
	 * @param medical
	 */
	public void updateMedical(Medical medical);
	/**
	 * 删除药物
	 * @param medicalId
	 */
	public void deleteMedicalById(String medicalId);
	/**
	 * 获取某人所有药物列表
	 * @param userId
	 * @return
	 */
	public List<Medical> queryUserMedicals(String userId);
	/**
	 * 保存吃药时间
	 * @param medical
	 */
	public void saveMedicalTime(MedicalTime medicalTime);
	/**
	 * 更新吃药时间
	 * @param medicalTime
	 */
	public void updateMedicalTime(MedicalTime medicalTime);
	/**
	 * 根据药物id,删除药物时间
	 * @param medicalId
	 */
	public void deleteMedicalTimeByMedicalId(String medicalId);
	/**
	 * 获取药物时间
	 * @param medicalId
	 * @return
	 */
	public List<MedicalTime> queryMedicalTimeList(String medicalId);
	/**
	 * 根据主键删除药物时间
	 * @param medicalTimeId
	 */
	public void deleteMedicalTimeById(String medicalTimeId);
	/**
	 * 保存吃药记录
	 * @param medicalTake
	 */
	public void saveMedicalTake(MedicalTake medicalTake);
	/**
	 * 更新吃药记录
	 * @param medicalTake
	 */
	public void updateMedicalTake(MedicalTake medicalTake);
	/**
	 * 获取存在的
	 * @param medicalIdArray
	 * @return
	 */
	public String getExistMedicalIds(String medicalIds);
	/**
	 * 指定成员，获取每页的药物
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<Medical> queryPageMedicalsByUser(String userId, Page page);
	/**
	 * 
	 * @param accountId
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	public List<Medical> queryPageMedicalsByAccount(String accountId, long startTime, long endTime, Page page);
	/**
	 * 指定时间段内，获取每页的药物时间
	 * @param startTime
	 * @param endTime
	 * @param page 当前页
	 * @param pageSize
	 * @return
	 */
	public List<MedicalTime> queryPageMedicalTimesByAccount(String accountId, long startTime, long endTime, Page page);
	
	/**
	 * 指定时间段内，获取每页的药物记录
	 * @param startTime
	 * @param endTime
	 * @param page 当前页
	 * @param pageSize
	 * @return
	 */
	public List<MedicalTake> queryPageMedicalTakesByAccount(String accountId, long startTime, long endTime, Page page);
	

}
