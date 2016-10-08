package com.medical.maintain.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medical.common.util.Page;
import com.medical.maintain.service.MedicalService;
import com.medical.maintain.dao.MedicalDao;
import com.medical.maintain.entity.Medical;
import com.medical.maintain.entity.MedicalTake;
import com.medical.maintain.entity.MedicalTime;
/**
 * 药物service
 * @author 
 * @time 下午11:19:30
 */
@Service
public class MedicalServiceImpl implements MedicalService{
	@Autowired
	private MedicalDao medicalDao;
	@Override
	public Medical getMedicalById(String medicalId){
		return medicalDao.getMedicalById(medicalId);
	}
	@Override
	@Transactional
	public void saveMedical(Medical medical){
		medicalDao.saveMedical(medical);
	}
	@Override
	@Transactional
	public void saveMedical(Medical medical, List<MedicalTime> medicalTimeList){
		medicalDao.saveMedical(medical);
		
		if(medicalTimeList == null || medicalTimeList.size() == 0){
			return;
		}
		for(MedicalTime medicalTime : medicalTimeList){
			medicalTime.setMedicalId(medical.getMedicalId());
			medicalDao.saveMedicalTime(medicalTime);
		}
	}
	@Override
	@Transactional
	public void updateMedical(Medical medical){
		medicalDao.updateMedical(medical);
	}
	@Override
	@Transactional
	public void deleteMedicalById(String medicalId){
		medicalDao.deleteMedicalTimeByMedicalId(medicalId);
		medicalDao.deleteMedicalById(medicalId);
	}
	@Override
	public List<Medical> queryUserMedicals(String userId){
		return medicalDao.queryUserMedicals(userId);
	}
	@Override
	@Transactional
	public void saveMedicalTime(MedicalTime medicalTime){
		medicalDao.saveMedicalTime(medicalTime);
	}
	@Override
	@Transactional
	public void updateMedicalTime(MedicalTime medicalTime){
		medicalDao.updateMedicalTime(medicalTime);
		
	}
	@Override
	@Transactional
	public void deleteMedicalTimeByMedicalId(String medicalId){
		medicalDao.deleteMedicalTimeByMedicalId(medicalId);
	}
	@Override
	public List<MedicalTime> queryMedicalTimeList(String medicalId){
		return medicalDao.queryMedicalTimeList(medicalId);
	}
	@Override
	@Transactional
	public void deleteMedicalTimeById(String medicalTimeId){
		medicalDao.deleteMedicalTimeById(medicalTimeId);
	}
	@Override
	@Transactional
	public void saveMedicalTake(MedicalTake medicalTake){
		medicalDao.saveMedicalTake(medicalTake);
	}
	@Override
	@Transactional
	public void updateMedicalTake(MedicalTake medicalTake){
		medicalDao.updateMedicalTake(medicalTake);
	}
	@Override
	public String getExistMedicalIds(String medicalIds){
		return medicalDao.getExistMedicalIds(medicalIds);
	}
	@Override
	public List<Medical> queryPageMedicalsByUser(String userId, Page page){
		return medicalDao.queryPageMedicalsByUser(userId, page);
	}
	@Override
	public List<Medical> queryPageMedicalsByAccount(String accountId, long startTime, long endTime, Page page){
		return medicalDao.queryPageMedicalsByAccount(accountId, startTime, endTime, page);
	}
	@Override
	public List<MedicalTime> queryPageMedicalTimesByAccount(String accountId, long startTime, long endTime, Page page){
		return medicalDao.queryPageMedicalTimesByAccount(accountId, startTime, endTime, page);
	}
	@Override
	public List<MedicalTake> queryPageMedicalTakesByAccount(String accountId, long startTime, long endTime, Page page){
		return medicalDao.queryPageMedicalTakesByAccount(accountId, startTime, endTime, page);
	}
	


}
