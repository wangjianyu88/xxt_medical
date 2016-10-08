package com.medical.maintain.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.medical.common.bean.PageBean;
import com.medical.common.util.BaseControl;
import com.medical.common.util.Page;
import com.medical.common.util.Util;
import com.medical.maintain.entity.Medical;
import com.medical.maintain.service.MedicalService;
/**
 * 药物管理类
 * @author 
 * @time 下午7:30:11
 */
@Controller
@Scope("session")
public class MedicalListControl extends BaseControl{
	@Autowired
	private MedicalService medicalService;
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(MedicalOperatorControl.class);
	/**
	 * 药物列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/maintain/medicalList.do")
	public String medicalList(HttpServletRequest request,HttpServletResponse response, PageBean<Map<String, String>> pageBean, ModelMap map){
		//设置session
		super.setSessionUser(request, map);
		
		return "maintain/medicalList";
	}
	/**
	 * 分页获取药物列表
	 * @param request
	 * @param response
	 * @param pageBean
	 */
	@RequestMapping("/maintain/ajaxMedicalList.do")
	public void ajaxMedicalList(HttpServletRequest request,HttpServletResponse response, PageBean<Medical> pageBean){
		//设置page
		Page page = (Page)pageBean;
		List<Medical> medicalList = medicalService.queryPageMedicalsByUser(super.familyUser.getUserId(), page);
		if(medicalList == null){
			medicalList = new ArrayList<Medical>();
		}
		pageBean.setItemList(medicalList);
		Util.writeUtf8JSON(response, Util.toJsonStr(pageBean));
	}
	
}
