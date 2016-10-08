package com.medical.family.control;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medical.common.util.BaseControl;
import com.medical.common.util.Util;
import com.medical.family.entity.FamilyUser;
import com.medical.family.service.FamilyService;
import com.medical.synchrodata.bean.Result;

@Controller
@Scope("session")
/**
 * 家庭成员管理
 * @author 
 * @time 下午8:46:40
 */
class FamilyUserControl extends BaseControl{
	@Autowired
	private FamilyService familyService;
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(FamilyUserControl.class);
	
	/**
	 * 获取家庭成员列表
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping("/family/familyUserList.do")
	public String familyUserList(HttpServletRequest request,HttpServletResponse response, ModelMap map){
		//设置session
		super.setSessionUser(request, map);
		//获取家庭成员
		List<FamilyUser> familyUserList = familyService.queryFamilyUsers(super.account.getAccountId());
		map.put("familyUserList", familyUserList);
		return "family/familyUserList";
	}
	/**
	 * 家庭成员准备添加
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping("/family/familyUserAdd.do")
	public String familyUserAdd(HttpServletRequest request,HttpServletResponse response,FamilyUser newFamilyUser, ModelMap map){
		//设置session
		super.setSessionUser(request, map);
		return "family/familyUserAdd";
	}
	/**
	 * 提交添加的家庭成员
	 * @param request
	 * @param response
	 * @param newFamilyUser
	 * @param map
	 * @return
	 */
	@RequestMapping("/family/familyUserAddSubmit.do")
	public void familyUserAddSubmit(HttpServletRequest request,HttpServletResponse response,FamilyUser newFamilyUser, ModelMap map){
		if(newFamilyUser == null || newFamilyUser.getName() == null || newFamilyUser.getName().length() == 0){
			Util.writeUtf8JSON(response, Util.toJsonStr(super.getSynResponse(Result.SYN_FAIL, "家庭成员名不能为空")));
			return;
		}
		//获取家庭成员列表
		List<FamilyUser> familyUserList = familyService.queryFamilyUsers(super.account.getAccountId());
		if(isInFamilyUserList(newFamilyUser.getName(), familyUserList)){
			Util.writeUtf8JSON(response, Util.toJsonStr(super.getSynResponse(Result.SYN_FAIL, "不能添加相同的成员名")));
			return;
		}
		newFamilyUser.setAccountId(super.account.getAccountId());
		newFamilyUser.setStatus(1);
		newFamilyUser.setType(1);
		newFamilyUser.setLastUpdateTime(new Date().getTime());
		familyService.saveFamilyUser(newFamilyUser);
		Util.writeUtf8JSON(response, Util.toJsonStr(super.getSuccessSynResponse()));
	}
	/**
	 * 家庭成员身份切换
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping("/family/familyUserChange.do")
	public String familyUserChange(HttpServletRequest request,HttpServletResponse response, ModelMap map){
		super.setSessionUser(request, map);
		//获取家庭成员
		List<FamilyUser> familyUserList = familyService.queryFamilyUsers(super.account.getAccountId());
		map.put("familyUserList", familyUserList);
		return "family/familyUserChange";
	}
	/**
	 * 家庭成员身份切换提交
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping("/family/familyUserChangeSubmit.do")
	public void familyUserChangeSubmit(HttpServletRequest request,HttpServletResponse response,FamilyUser newFamilyUser){
		//获取家庭成员
		newFamilyUser = familyService.getFamilyUser(newFamilyUser.getUserId());
		if(newFamilyUser == null){
			Util.writeUtf8JSON(response, Util.toJsonStr(super.getSynResponse(Result.SYN_FAIL, "不存在此家庭成员")));
			return;
		}
		long lastUpdateTime = new Date().getTime();
		//老当前成员切换为非当前
		super.familyUser.setStatus(1);
		super.familyUser.setLastUpdateTime(lastUpdateTime);
		familyService.updateFamilyUser(super.familyUser);
		//该成员切换为当前成员
		newFamilyUser.setStatus(2);
		newFamilyUser.setLastUpdateTime(new Date().getTime());
		familyService.updateFamilyUser(newFamilyUser);
		//设置新的身份
		super.session.removeAttribute("familyUser");
		super.session.setAttribute("familyUser", newFamilyUser);
		Util.writeUtf8JSON(response, Util.toJsonStr(super.getSuccessSynResponse()));
		
	}
	/**
	 * 成员名是否已经在家庭成员列表中了
	 * @param name
	 * @param familyUserList
	 * @return
	 */
	private boolean isInFamilyUserList(String name, List<FamilyUser> familyUserList){
		if(familyUserList == null){
			return false;
		}
		for(FamilyUser familyUser : familyUserList){
			if(familyUser.getName().equals(name)){
				return true;
			}
		}
		return false;
	}

}
