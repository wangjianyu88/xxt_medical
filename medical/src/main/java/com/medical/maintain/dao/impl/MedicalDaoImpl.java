package com.medical.maintain.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Date;
import com.medical.common.basedao.BaseDao;
import com.medical.maintain.entity.Medical;
import com.medical.maintain.entity.MedicalTime;
import com.medical.maintain.entity.MedicalTake;
import com.medical.maintain.dao.MedicalDao;
import com.medical.common.util.Page;

import java.util.List;
@Repository
public class MedicalDaoImpl implements MedicalDao{
	@Autowired
    private BaseDao  baseDao;
	@Override
	public Medical getMedicalById(String medicalId){
		return (Medical)baseDao.getObjectById(Medical.class, medicalId);
	}
	@Override
	public void saveMedical(Medical medical){
		baseDao.create(medical);
	}
	@Override
	public void updateMedical(Medical medical){
		baseDao.update(medical);
	}
	@Override
	public void deleteMedicalById(String medicalId){
		String sql = "update t_md_medical set status = 0, last_update_time = ?, modify_date = ? where medical_id = ?" ;
		Date date = new Date();
		Object[] params={date.getTime(), date.getTime(), medicalId};
		baseDao.updateBySql(sql, params);
	}
	@Override
	public void saveMedicalTime(MedicalTime medicalTime){
		baseDao.create(medicalTime);
	}
	@Override
	public void updateMedicalTime(MedicalTime medicalTime){
		baseDao.update(medicalTime);
		
	}
	@Override
	public void deleteMedicalTimeByMedicalId(String medicalId){
		String sql = "update t_md_medical_time set status = 0, last_update_time = ?, modify_date = ? where medical_id = ?" ;
		Date date = new Date();
		Object[] params={date.getTime(), date.getTime(), medicalId};
		baseDao.updateBySql(sql, params);
	}
	@Override
	public void deleteMedicalTimeById(String medicalTimeId){
		String sql = "update t_md_medical_time set status = 0, last_update_time = ?, modify_date = ? where medical_time_id = ?" ;
		Date date = new Date();
		Object[] params={date.getTime(), date.getTime(), medicalTimeId};
		baseDao.updateBySql(sql, params);
	}
	@Override
	public void saveMedicalTake(MedicalTake medicalTake){
		baseDao.create(medicalTake);
	}
	@Override
	public void updateMedicalTake(MedicalTake medicalTake){
		baseDao.update(medicalTake);
	}
	@Override
	public String getExistMedicalIds(String medicalIds){
		String sql = "select group_concat(medical_id) medicalIds  from t_md_medical where medical_id in("+medicalIds+") ";
		String existMedicalIds = (String)baseDao.getObjectBySQL(sql, null);
		return existMedicalIds;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Medical> queryPageMedicalsByUser(String userId, Page page){
		String selectCount = "select count(medicalId) from Medical where userId = ? and status != 0";
		String select = " from Medical  where userId = ? and status != 0 order by lastUpdateTime desc";
		Object[] values = {userId};
		return (List<Medical>)baseDao.getListByHQL(selectCount, select, values, page);
	}
	@SuppressWarnings("unchecked")
	public List<Medical> queryPageMedicalsByAccount(String accountId, long startTime, long endTime, Page page){
		String selectCount = "select count(*) from t_md_medical m, t_md_family_user fu where fu.account_id = ? and m.last_update_time > ? and m.last_update_time <= ? and m.user_id = fu.user_id ";
		String select = " select m.* from t_md_medical m, t_md_family_user fu where fu.account_id = ? and m.last_update_time > ? and m.last_update_time <= ? and m.user_id = fu.user_id order by m.last_update_time desc";
		Object[] values = {accountId, startTime, endTime};
		return (List<Medical>)baseDao.getListBySQL(selectCount, select, values, page, Medical.class);
		//return (List<Medical>)baseDao.getListByHQL(selectCount, select, values, page);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Medical> queryUserMedicals(String userId){
		String select = " from Medical  where userId = ? and status != 0 order by lastUpdateTime desc";
		Object[] values = {userId};
		return (List<Medical>)baseDao.getListByHQL(select, values);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<MedicalTime> queryPageMedicalTimesByAccount(String accountId, long startTime, long endTime, Page page){
		String select = " select mt.* from t_md_medical m, t_md_family_user fu, t_md_medical_time mt where fu.account_id = ? and mt.last_update_time > ? and mt.last_update_time <= ?  and m.user_id = fu.user_id and mt.medical_id = m.medical_id order by mt.last_update_time desc ";
		String selectCount = " select count(mt.medical_time_id) from t_md_medical m, t_md_family_user fu, t_md_medical_time mt where fu.account_id = ? and mt.last_update_time > ? and mt.last_update_time <= ?  and m.user_id = fu.user_id and mt.medical_id = m.medical_id ";
		Object[] values = {accountId, startTime, endTime};
		return (List<MedicalTime>)baseDao.getListBySQL(selectCount, select, values, page, MedicalTime.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<MedicalTime> queryMedicalTimeList(String medicalId){
		String select = "from MedicalTime where medicalId = ? and status = 1 order by whichTimes";
		Object[] values = {medicalId};
		return (List<MedicalTime>)baseDao.getListByHQL(select, values);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<MedicalTake> queryPageMedicalTakesByAccount(String accountId, long startTime, long endTime, Page page){
		String select = " select mt.* from t_md_medical m, t_md_family_user fu, t_md_medical_take mt where fu.account_id = ? and mt.last_update_time > ? and mt.last_update_time <= ?  and m.user_id = fu.user_id and mt.medical_id = m.medical_id order by mt.last_update_time desc ";
		String selectCount = " select count(mt.medical_take_id) from t_md_medical m, t_md_family_user fu, t_md_medical_take mt where fu.account_id = ? and mt.last_update_time > ? and mt.last_update_time <= ?  and m.user_id = fu.user_id and mt.medical_id = m.medical_id ";
		Object[] values = {accountId, startTime, endTime};
		return (List<MedicalTake>)baseDao.getListBySQL(selectCount, select, values, page, MedicalTake.class);
	}
}
