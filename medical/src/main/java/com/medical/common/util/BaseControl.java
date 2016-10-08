package com.medical.common.util;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import com.medical.synchrodata.bean.Result;
import com.medical.synchrodata.bean.SynResponse;
import com.medical.common.bean.PageBean;
import com.medical.family.entity.FamilyUser;
import com.medical.login.entity.Account;
/**
 * 基础类
 * @author 
 * @time 下午7:35:39
 */
public class BaseControl {
	/*账号*/
	public Account account;
	/*用户*/
	public FamilyUser familyUser;
	/*session*/
	public HttpSession session;
	/**
	 * 日期映射转换类
	 * @param binder
	 * @param request
	 */
 	@InitBinder
    public void initBinder(WebDataBinder binder,WebRequest request) {
        binder.registerCustomEditor(Date.class, FreeworkDateEditor.INSTANCE);
    }
	/**
	 * 成功的返回
	 * @param resultStatus
	 * @param resultInfo
	 * @return
	 */
	public SynResponse<Object> getSuccessSynResponse(){
		return getSynResponse(Result.SYN_SUCCESS, null, null, null);
	} 
	/**
	 * 失败的返回
	 * @param resultStatus
	 * @param resultInfo
	 * @return
	 */
	public SynResponse<Object> getFailSynResponse(){
		return getSynResponse(Result.SYN_FAIL, null, null, null);
	}
 	/**
 	 * 
 	 * @param resultStatus
 	 * @param resultInfo
 	 * @return
 	 */
	public SynResponse<Object> getSynResponse(String resultStatus, String resultInfo){
		return getSynResponse(resultStatus, resultInfo, null, null);
	} 
	/**
	 * 基础的构造函数
	 * @param resultStatus
	 * @param resultInfo
	 * @param synMap
	 * @param synList
	 * @return
	 */
	public SynResponse<Object> getSynResponse(String resultStatus, String resultInfo,Map<String, String> synMap, List<Object> synList){
		if(resultInfo == null){
			if(Result.SYN_SUCCESS.equals(resultStatus)){
				resultInfo = "操作成功";
			}else{
				resultInfo = "操作失败";
			}
		}
		Result result = new Result(resultStatus, resultInfo);
		if(synMap == null){
			synMap = new HashMap<String, String>();
		}
		if(synList == null){
			synList = new ArrayList<Object>();
		}
		SynResponse<Object> synResponse = new SynResponse<Object>();
		synResponse.setResult(result);
		synResponse.setSynMap(synMap);
		synResponse.setSynList(synList);
		return synResponse;
	} 
	/**
	 * 获取登录的session信息
	 * @param request
	 */
	public void setSessionUser(HttpServletRequest request, ModelMap map){
		this.session = request.getSession();
    	this.account = (Account) this.session.getAttribute("account");
    	this.familyUser = (FamilyUser) this.session.getAttribute("familyUser");
    	if(map != null){
    		map.put("familyUser", this.familyUser);
    	}
    }
	

}
