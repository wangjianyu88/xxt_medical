package com.medical.remind.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.medical.maintain.entity.Medical;

public class RemindCompute {
	/**
	 * 给出某天，获取某个药物某天需要提醒的列表
	 * @param dateTime
	 * @param medical
	 * @return
	 */
	public static List<Medical> queryRemindMedical(long dateTime, Medical medical){
		List<Medical> medicalList  = new ArrayList<Medical>();
		if(medical == null || medical.getTakeTime() == null || medical.getTakeTime().length() == 0){
			return medicalList;
		}
		//该药物这天不需要提醒
		if(!isRemindAtDate(dateTime, medical)){
			return medicalList;
		}
		//服药的日期
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		Date date = new Date();
		date.setTime(dateTime);
	    String dateFormate = sdf.format(date);
	    
		String[] takeTimeArray = medical.getTakeTime().split(",");
		for(String takeTime : takeTimeArray){
			Medical medicalTemp = new Medical();
			medicalTemp.setClientMedicalId(medical.getClientMedicalId());
			medicalTemp.setDays(medical.getDays());
			medicalTemp.setEatTime(medical.getEatTime());
			medicalTemp.setEndDate(medical.getEndDate());
			medicalTemp.setLastUpdateTime(medical.getLastUpdateTime());
			medicalTemp.setMedicalId(medical.getMedicalId());
			medicalTemp.setName(medical.getName());
			medicalTemp.setRemark(medical.getRemark());
			medicalTemp.setSplitDays(medical.getSplitDays());
			medicalTemp.setStartDate(medical.getStartDate());
			medicalTemp.setStatus(medical.getStatus());
			medicalTemp.setTakeNum(medical.getTakeNum());
			medicalTemp.setTakeTime(dateFormate + " " +takeTime);
			medicalTemp.setTakeUnit(medical.getTakeUnit());
			medicalTemp.setUserId(medical.getUserId());
			
			medicalList.add(medicalTemp);
		}
		return medicalList;
	}
	/**
	 * 获取一个药物提醒的日期距今天相距的天数，从大小排列
	 * @param todayTime
	 * @param medical
	 * @return
	 */
	public static Set<Integer> getRemindDays(long todayTime, Medical medical){
		Set<Integer> remindDaysSet = new HashSet<Integer>();
		int startBetweenDays = getBetweenDays(todayTime, medical.getStartDate().getTime());
		if(startBetweenDays < 0){
			return remindDaysSet;
		}
		int endBetweenDays = getBetweenDays(todayTime, medical.getEndDate().getTime());
		if(endBetweenDays < 0){
			endBetweenDays = 0;
		}
		for(int i=startBetweenDays; i>=endBetweenDays; ){
			if(isRemindAtDate(todayTime - 1000 * 3600 * 24, medical)){
				remindDaysSet.add(i);
			}
			i = i - (medical.getSplitDays() + 1);
		}
		return remindDaysSet;
	}
	/**
	 * 判断是否需要在指定的某天提醒
	 * @param dateTime 指定的日期
	 * @param medical
	 * @return
	 */
	private static boolean isRemindAtDate(long dateTime, Medical medical){
		//已经服用完了
		if(getBetweenDays(medical.getEndDate().getTime(), dateTime) < 0){
			return false;
		}
		int betweenDays = getBetweenDays(dateTime, medical.getStartDate().getTime());
		//还没开始
		if(betweenDays < 0){
			return false;
		}
		if(betweenDays % (medical.getSplitDays() + 1) == 0){
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 获取两个日期相隔的天数
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static int getBetweenDays(long time1, long time2){
		long betweenDays = (time1 - time2) / (1000 * 3600 * 24); 
		return Integer.parseInt(Long.toString(betweenDays));
	}
	
	public static void main(String[] args){
		Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		set.add(2);
		set.add(1);
		System.out.println(set);
	}

}
