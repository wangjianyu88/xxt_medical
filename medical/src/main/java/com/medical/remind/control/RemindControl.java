package com.medical.remind.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medical.common.bean.PageBean;
import com.medical.common.util.BaseControl;
import com.medical.common.util.EncryptDeciphering;
import com.medical.common.util.Util;
import com.medical.login.entity.Account;
import com.medical.maintain.entity.Medical;
import com.medical.maintain.service.MedicalService;
import com.medical.register.service.RegisterService;
import com.medical.remind.service.impl.RemindCompute;
import com.medical.synchrodata.bean.Result;
import com.medical.synchrodata.bean.SynResponse;
/**
 * 提醒
 * @author 
 * @time 下午4:59:41
 */
@Controller
@Scope("session")
public class RemindControl extends BaseControl{
	@Autowired
	private MedicalService medicalService;
	@Autowired
	private RegisterService registerService;
	/**
	 * 今天的药物提醒
	 * @param request
	 * @param response
	 */
	@RequestMapping("/remind/remindToday.do")
	public String remindToday(HttpServletRequest request,HttpServletResponse response, ModelMap map){
		//设置session
		super.setSessionUser(request, map);
		long todayTime = new Date().getTime();
		//今天需要提醒的药物列表
		List<Medical> remindMedicalList = new ArrayList<Medical>();
		List<Medical> medicalList = medicalService.queryUserMedicals(super.familyUser.getUserId());
		
		for(Medical medical : medicalList){
			remindMedicalList.addAll(RemindCompute.queryRemindMedical(todayTime, medical));
		}
		map.put("medicalList", remindMedicalList);
		return "remind/remindToday";
		
	}
	/**
	 * 历史药物提醒，不包括今天
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping("/remind/remindHistory.do")
	public String remindHistory(HttpServletRequest request,HttpServletResponse response, ModelMap map){
		//设置session
		super.setSessionUser(request, map);
		return "remind/remindHistory";
	}
	/**
	 * ajax请求今日提醒
	 * @param request
	 * @param response
	 * @param pageBean
	 */
	@RequestMapping("/remind/ajaxRemindHistory.do")
	public void ajaxRemindHistory(HttpServletRequest request,HttpServletResponse response, PageBean<Medical> pageBean){
		//最终生成的提醒列表
		List<Medical> remindMedicalList = getRemindMedicalList();
		//设置分页结果
		computePage(pageBean, remindMedicalList);
		Util.writeUtf8JSON(response, Util.toJsonStr(pageBean));
	}
	/**
	 * 修改密码提交
	 * @param request
	 * @param response
	 * @param oldPassword
	 * @param newPassword
	 * @param map
	 * @return
	 */
	@RequestMapping("/remind/updatePasswordIndex.do")
	public String updatePasswordIndex(HttpServletRequest request, HttpServletResponse response, ModelMap map){
		//设置session
		super.setSessionUser(request, map);
		return "remind/updatePasswordIndex";
	}
	/**
	 * 修改密码提交
	 * @param request
	 * @param response
	 * @param oldPassword
	 * @param newPassword
	 * @param map
	 * @return
	 */
	@RequestMapping("/remind/updatePassword.do")
	public void updatePassword(HttpServletRequest request,HttpServletResponse response,  Account account, String newPassword, ModelMap map){
		//设置session
		super.setSessionUser(request, null);
		Account oldAccount = registerService.getAccountByPassword(super.account.getUsername(), EncryptDeciphering.getInstance().encrypt(account.getPassword()));
		SynResponse<Object> synResponse = null;
		//用户名或密码不对
		if(oldAccount == null){
			synResponse = super.getSynResponse(Result.SYN_FAIL, "用户名或密码不对");
		}else{
			long lastUpdateTime = new Date().getTime();
			//加密密码
			String encryptNewPassword = EncryptDeciphering.getInstance().encrypt(newPassword);
			oldAccount.setPassword(encryptNewPassword);
			oldAccount.setLastUpdatetime(lastUpdateTime);
			registerService.updateAccount(oldAccount);
			synResponse = super.getSuccessSynResponse();
			
			super.session.removeAttribute("account");
			super.session.removeAttribute("familyUser");
			
			synResponse = super.getSuccessSynResponse();
		}
		Util.writeUtf8JSON(response, Util.toJsonStr(synResponse));
	}
	/**
	 * 获总的提醒列表
	 * @return
	 */
	private List<Medical> getRemindMedicalList(){
		long todayTime = new Date().getTime();
		//最终生成的提醒列表
		List<Medical> remindMedicalList = new ArrayList<Medical>();
		//药物列表
		List<Medical> medicalList = medicalService.queryUserMedicals(super.familyUser.getUserId());
		//需要提醒的天数汇总
		Set<Integer> remindDaysSet = getRemindDays(medicalList);
		//从小到大排列
		List<Integer> remindDaysList = sortRemindDays(remindDaysSet);
		for(int remindDay : remindDaysList){
			//今日提醒不在列表中
			if(remindDay == 0){
				continue;
			}
			//最多展示3年内的
			if(remindDay > 365 * 3){
				break;
			}
			//提醒的天
			long remindTime = todayTime - remindDay * 1000 * 3600 * 24;
			for(Medical medical : medicalList){
				remindMedicalList.addAll(RemindCompute.queryRemindMedical(remindTime, medical));
			}
		}
		return remindMedicalList;
	}
	/**
	 * 相对于今天，获取需要提醒的日期距现在相差的天数
	 * @param medicalList
	 * @return
	 */
	private Set<Integer> getRemindDays(List<Medical> medicalList){
		long todayTime = new Date().getTime();
		Set<Integer> remindDaysSet = new HashSet<Integer>();
		if(medicalList == null){
			return remindDaysSet;
		}
		for(Medical medical : medicalList){
			Set<Integer> medicalRemindDyasSet= RemindCompute.getRemindDays(todayTime, medical);
			remindDaysSet.addAll(medicalRemindDyasSet);
		}
		return remindDaysSet;
	}
	/**
	 * 转化成从小到大排列的List
	 * @param remindDaysSet
	 * @return
	 */
	private List<Integer> sortRemindDays(Set<Integer> remindDaysSet){
		List<Integer> remindDaysList = new ArrayList<Integer>();
		if(remindDaysSet == null){
			return remindDaysList;
		}
		for(int remindDay : remindDaysSet){
			boolean matchFlag = false;
			for(int i = 0; i < remindDaysList.size(); i++){
				if(remindDay < remindDaysList.get(i)){
					remindDaysList.add(i, remindDay);
					matchFlag = true;
					break;
				}
			}
			if(!matchFlag){
				remindDaysList.add(remindDay);
			}
		}
		return remindDaysList;
	}
	/**
	 * 对于取出总数的集合，计算分页结果集
	 * @param pageBean
	 * @param totalItemList
	 */
	private void computePage(PageBean<Medical> pageBean, List<Medical> totalItemList){
		if(pageBean == null ){
			pageBean = new PageBean<Medical>();
			return;
		}
		pageBean.setTotal(totalItemList.size());
		int fromIndex = pageBean.getPage() * pageBean.getRp();
		int toIndex = (pageBean.getPage() + 1) * pageBean.getRp();
		if(toIndex > pageBean.getTotal()){
			toIndex = pageBean.getTotal();
		}
		if(totalItemList.size() == 0){
			fromIndex = 0;
			toIndex = 0;
		}
		pageBean.setItemList(totalItemList.subList(fromIndex, toIndex));
	}
	

}
