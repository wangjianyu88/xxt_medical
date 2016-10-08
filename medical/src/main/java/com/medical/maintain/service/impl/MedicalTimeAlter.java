package com.medical.maintain.service.impl;


import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import com.medical.maintain.entity.MedicalTime;
import com.medical.maintain.bean.MedicalInfo;
import com.medical.maintain.service.MedicalService;

/**
 * 药物时间的处理
 * @author 
 * @time 下午10:30:38
 */
public class MedicalTimeAlter {
	private MedicalService medicalService;
	private MedicalInfo medicalInfo;
	private List<MedicalTime> updateMedicalTimeList = new ArrayList<MedicalTime>();
	private List<MedicalTime> insertMedicalTimeList = new ArrayList<MedicalTime>();
	public MedicalTimeAlter(MedicalInfo medicalInfo,MedicalService medicalService){
		this.medicalService = medicalService;
		this.medicalInfo = medicalInfo;
	}
	/**
	 * 药物时间的主干函数
	 */
	public void doAlter(){
		if(medicalInfo.getTakeTime() == null || medicalInfo.getTakeTime().length() == 0){
			return;
		}
		long lastUpdateTime = new Date().getTime();
		String[] takeTimeArray = medicalInfo.getTakeTime().split(",");
		String[] whichTimeArray = medicalInfo.getWhichTimes().split(",");
		String[] medicalTimeIdArray = medicalInfo.getMedicaTimeIds().split(",");
		List<MedicalTime> medicalTimeList= medicalService.queryMedicalTimeList(medicalInfo.getMedicalId());
		if(medicalTimeList == null){
			medicalTimeList = new ArrayList<MedicalTime>();
		}
		//过滤哪些是修改的，哪些是新增的
		for(int i=0; i<takeTimeArray.length; i++){
			MedicalTime medicalTime = getMedicalTime(medicalTimeIdArray[i], medicalInfo.getMedicalId(), takeTimeArray[i], Integer.parseInt(whichTimeArray[i]), 1, lastUpdateTime);
			if(medicalTimeIdArray[i] == null || medicalTimeIdArray[i].length() == 0 || medicalTimeIdArray[i].equals("new")){
				medicalTime.setMedicalTimeId(null);
				insertMedicalTimeList.add(medicalTime);
			}else{
				MedicalTime updateMedicalTime = getUpdateMedicalTime(medicalTime, medicalTimeList);
				//没有修改，不需要保存
				if(updateMedicalTime == null){
					continue;
				}
				updateMedicalTimeList.add(updateMedicalTime);
			}
		}
		/**
		 * 需要删除的
		 */
		for(int i=0; i<medicalTimeList.size(); i++){
			if(!inMedicalTimeIdArray(medicalTimeList.get(i).getMedicalTimeId(), medicalTimeIdArray)){
				medicalTimeList.get(i).setStatus(0);
				medicalTimeList.get(i).setLastUpdateTime(lastUpdateTime);
				updateMedicalTimeList.add(medicalTimeList.get(i));
			}
		}
	}
	/**
	 * 构造MedicalTime
	 * @param medicalId
	 * @param takeTime
	 * @param whichTime
	 * @param status
	 * @param lastUpdateTime
	 * @return
	 */
	private MedicalTime getMedicalTime(String medicalTimeId, String medicalId, String takeTime, int whichTime, int status, long lastUpdateTime){
		MedicalTime medicalTime = new MedicalTime();
		medicalTime.setMedicalTimeId(medicalTimeId);
		medicalTime.setMedicalId(medicalInfo.getMedicalId());
		medicalTime.setTakeTime(takeTime);
		medicalTime.setWhichTimes(whichTime);
		medicalTime.setStatus(status);
		medicalTime.setLastUpdateTime(lastUpdateTime);
		return medicalTime;
	}
	/**
	 * 判断是否相同
	 * @param medicalTime
	 * @param medicalTimeList
	 * @return
	 */
	private MedicalTime getUpdateMedicalTime(MedicalTime medicalTime, List<MedicalTime> medicalTimeList){
		for(MedicalTime node : medicalTimeList){
			if(!node.getMedicalTimeId().equals(medicalTime.getMedicalTimeId())){
				continue;
			}
			if(node.getTakeTime().equals(medicalTime.getTakeTime()) && node.getWhichTimes() == medicalTime.getWhichTimes()){
				return null;
			}else{
				node.setLastUpdateTime(medicalTime.getLastUpdateTime());
				node.setMedicalId(medicalTime.getMedicalId());
				node.setStatus(medicalTime.getStatus());
				node.setTakeTime(medicalTime.getTakeTime());
				node.setWhichTimes(medicalTime.getWhichTimes());
				node.setModifyDate(new Date().getTime());
				return node;
			}
		}
		return null;
	}
	/**
	 * 是否在数组中
	 * @param medicalTimeId
	 * @param medicalTimeIdArray
	 * @return
	 */
	private boolean inMedicalTimeIdArray(String medicalTimeId, String[] medicalTimeIdArray){
		for(String node : medicalTimeIdArray){
			if(node.equals(medicalTimeId)){
				return true;
			}
		}
		return false;
	}
	public List<MedicalTime> getUpdateMedicalTimeList() {
		return updateMedicalTimeList;
	}
	public void setUpdateMedicalTimeList(List<MedicalTime> updateMedicalTimeList) {
		this.updateMedicalTimeList = updateMedicalTimeList;
	}
	public List<MedicalTime> getInsertMedicalTimeList() {
		return insertMedicalTimeList;
	}
}
