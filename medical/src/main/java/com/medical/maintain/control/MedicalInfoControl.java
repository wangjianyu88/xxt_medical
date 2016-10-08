package com.medical.maintain.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.ModelMap;

import com.medical.common.util.BaseControl;
import com.medical.common.util.Util;
import com.medical.maintain.entity.Medical;
import com.medical.maintain.entity.MedicalTime;
import com.medical.maintain.service.MedicalService;
/**
 * 药物详情
 * @author 
 * @time 下午7:06:20
 */
@Controller
@Scope("session")
public class MedicalInfoControl extends BaseControl{
	@Autowired
	private MedicalService medicalService;
	@RequestMapping("/maintain/medicalInfo.do")
	public String medicalInfo(HttpServletRequest request,HttpServletResponse response, Medical medical, ModelMap map){
		//设置session
		super.setSessionUser(request, map);
		medical = medicalService.getMedicalById(medical.getMedicalId());
		List<MedicalTime> medicalTimeList =medicalService.queryMedicalTimeList(medical.getMedicalId());
		map.put("medical", medical);
		map.put("medicalTimeList", medicalTimeList);
		return "maintain/medicalInfo";
	}
	@RequestMapping("/maintain/toAddOrEditMedical.do")
	public String toAddOrEditMedical(HttpServletRequest request,HttpServletResponse response, Medical medical, ModelMap map){
		//设置session
		super.setSessionUser(request, map);
		List<MedicalTime> medicalTimeList = null;
		if(medical != null && medical.getMedicalId() != null){
			medical = medicalService.getMedicalById(medical.getMedicalId());
			medicalTimeList = medicalService.queryMedicalTimeList(medical.getMedicalId());
		}
		if(medicalTimeList == null){
			medicalTimeList = new ArrayList<MedicalTime>();
		}
		map.put("medical",  medical);
		map.put("medicalTimeList", medicalTimeList);
		return "maintain/medicalAddOrEdit";
	}

}
