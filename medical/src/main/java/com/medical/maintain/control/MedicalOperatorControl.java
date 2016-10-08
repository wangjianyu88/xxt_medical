package com.medical.maintain.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medical.common.util.Util;
import com.medical.maintain.entity.Medical;
import com.medical.maintain.entity.MedicalTime;
import com.medical.maintain.bean.MedicalInfo;
import com.medical.maintain.service.MedicalService;
import com.medical.common.util.BaseControl;
import com.medical.maintain.service.impl.MedicalTimeAlter;
/**
 * 药物保存类
 * @author 
 * @time 下午7:30:11
 */
@Controller
@Scope("session")
public class MedicalOperatorControl extends BaseControl{
	@Autowired
	private MedicalService medicalService;
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(MedicalOperatorControl.class);
	/**
	 * 药物保存
	 * @param request
	 * @param response
	 * @param medical
	 */
	@RequestMapping("/maintain/addMedical.do")
	public void addMedical(HttpServletRequest request, HttpServletResponse response, Medical medical){
		//设置session
		super.setSessionUser(request, null);
		medical.setUserId(super.familyUser.getUserId());
		medical.setEndDate(addDate(medical.getStartDate(), medical.getDays()));
		medicalService.saveMedical(medical, getMedicalTimeList(medical.getTakeTime()));
		Util.writeUtf8JSON(response, Util.toJsonStr(super.getSuccessSynResponse()));
	}
	/**
	 * 药物修改
	 * @param request
	 * @param response
	 * @param medical
	 */
	@RequestMapping("/maintain/updateMedical.do")
	public void updateMedical(HttpServletRequest request, HttpServletResponse response, MedicalInfo medicalInfo){
		super.setSessionUser(request, null);
		//药物时间处理，过滤出新增的和修改的
		MedicalTimeAlter medicalTimeAlter = new MedicalTimeAlter(medicalInfo, medicalService);
		medicalTimeAlter.doAlter();
		//新增的
		List<MedicalTime> insertMedicalTimeList = medicalTimeAlter.getInsertMedicalTimeList();
		for(MedicalTime medicalTime : insertMedicalTimeList){
			medicalService.saveMedicalTime(medicalTime);
		}
		//修改的(包含逻辑删除)
		List<MedicalTime> updateMedicalTimeList = medicalTimeAlter.getUpdateMedicalTimeList();
		for(MedicalTime medicalTime : updateMedicalTimeList){
			medicalService.updateMedicalTime(medicalTime);
		}
		medicalInfo.setEndDate(addDate(medicalInfo.getStartDate(), medicalInfo.getDays()));
		medicalInfo.setUserId(super.familyUser.getUserId());
		//保存修改药物
		Medical medical = getMedicalByInfo(medicalInfo);
		long nowTime = new Date().getTime();
		medical.setModifyDate(nowTime);
		medical.setLastUpdateTime(nowTime);
		medicalService.updateMedical(medical);
		Util.writeUtf8JSON(response, Util.toJsonStr(super.getSuccessSynResponse()));
	}
	/**
	 * 批量删除药物
	 * @param request
	 * @param response
	 * @param medical
	 */
	@RequestMapping("/maintain/deleteMedical.do")
	public void deleteMedical(HttpServletRequest request, HttpServletResponse response, String medicalIds){
		if(medicalIds == null || medicalIds.length() == 0){
			Util.writeUtf8JSON(response, Util.toJsonStr(super.getSuccessSynResponse()));
			return;
		}
		String[] medicalIdArray = medicalIds.split(",");
		for(String medicalId : medicalIdArray){
			medicalService.deleteMedicalById(medicalId);
		}
		Util.writeUtf8JSON(response, Util.toJsonStr(super.getSuccessSynResponse()));
	}
	/**
	 * 获取药物时间列表
	 * @param medicalTimes
	 * @param medicalId
	 * @return
	 */
	private List<MedicalTime> getMedicalTimeList(String medicalTimes){
		List<MedicalTime> medicalTimeList = new ArrayList<MedicalTime>();
		if(medicalTimes == null || medicalTimes.length() == 0){
			return medicalTimeList;
		}
		long lastUpdateTime = new Date().getTime();
		String[] medicalTimeArray = medicalTimes.split(",");
		for(int i=0; i<medicalTimeArray.length; i++){
			MedicalTime medicalTime = new MedicalTime();
			medicalTime.setTakeTime(medicalTimeArray[i]);
			medicalTime.setWhichTimes(i+1);
			medicalTime.setStatus(0);
			medicalTime.setLastUpdateTime(lastUpdateTime);
			medicalTimeList.add(medicalTime);
		}
		return medicalTimeList;
	}
	/**
	 * 从药物详情中获取药物实体
	 * @param medicalInfo
	 * @return
	 */
	private Medical getMedicalByInfo(MedicalInfo medicalInfo){
		Medical medical = new Medical();
		medical.setDays(medicalInfo.getDays());
		medical.setEatTime(medicalInfo.getEatTime());
		medical.setEndDate(medicalInfo.getEndDate());
		medical.setLastUpdateTime(medicalInfo.getLastUpdateTime());
		medical.setMedicalId(medicalInfo.getMedicalId());
		medical.setName(medicalInfo.getName());
		medical.setRemark(medicalInfo.getRemark());
		medical.setSplitDays(medicalInfo.getSplitDays());
		medical.setStartDate(medicalInfo.getStartDate());
		medical.setStatus(medicalInfo.getStatus());
		medical.setTakeNum(medicalInfo.getTakeNum());
		medical.setTakeTime(medicalInfo.getTakeTime());
		medical.setTakeUnit(medicalInfo.getTakeUnit());
		medical.setUserId(medicalInfo.getUserId());
		return medical;
	}
	/**
	 * 增加日期
	 * @param date
	 * @param interval
	 * @return
	 */
	private Date addDate(Date date, int interval){
		long time = date.getTime() + 86400000 * interval;
		Date newDate = new Date(time);
		return newDate;
	}
}
