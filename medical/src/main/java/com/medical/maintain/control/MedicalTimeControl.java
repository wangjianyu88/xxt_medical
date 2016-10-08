package com.medical.maintain.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.medical.common.util.BaseControl;

import com.medical.maintain.entity.MedicalTime;
import com.medical.maintain.service.MedicalService;
/**
 * 药物时间管理
 * @author 
 * @time 下午10:02:15
 */
@Controller
@Scope("session")
public class MedicalTimeControl extends BaseControl{
	@Autowired
	private MedicalService medicalService;
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param medicalTime
	 */

	public void deleteMedicalTime(HttpServletRequest request, HttpServletResponse response, MedicalTime medicalTime){
		
	}
}
