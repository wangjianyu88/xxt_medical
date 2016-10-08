package com.medical.login.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import com.medical.common.bean.ReturnResult;
import com.medical.common.util.EncryptDeciphering;
import com.medical.common.util.Util;
import com.medical.family.entity.FamilyUser;
import com.medical.family.service.FamilyService;
import com.medical.login.entity.Account;
import com.medical.login.bean.AccountBean;
import com.medical.login.service.LoginService;
import com.medical.register.service.RegisterService;
import com.medical.register.entity.*;
/**
 * 登录类
 * @author 
 * @time 上午10:00:43
 */
@Controller
@Scope("session")
public class LoginControl {
	@Autowired
	private LoginService loginService;
	@Autowired
	private RegisterService registerService;
	@Autowired
	private FamilyService familyService;
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LoginControl.class);
	@RequestMapping("/login/loginIndex.do")
	public String loginIndex(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession(true);
		Account account = (Account)session.getAttribute("account");
		if(account == null){
			return "login/login";
		}else{
			return "login/loginSuccess";
		}
	}
	/**
	 * 登录
	 * @param request
	 * @param response
	 * @param userBean
	 */
	@RequestMapping("/login/login.do")
	public void login(HttpServletRequest request,HttpServletResponse response, AccountBean accountBean){
		ReturnResult returnResult = new ReturnResult();
		//加密密码
		String password = EncryptDeciphering.getInstance().encrypt(accountBean.getPassword());
		Account account = loginService.login(accountBean.getUsername(), password);
		
		
		
		//登录成功
		if(account != null ){
			//设备号检查
			if(!checkAccountImei(account, accountBean)){
				returnResult.setRtnCode("haveUsed");
				returnResult.setRtnMsg("已在其他设备上使用");
				Util.writeUtf8JSON(response, Util.toJsonStr(returnResult));
				return;
			}
			
			FamilyUser familyUser=familyService.getActivityFamilyUser(account.getAccountId());
			setSession(request.getSession(true), account, familyUser);
			returnResult.setRtnCode("success");
			returnResult.setRtnMsg("登录成功");
			returnResult.setData(account);
			Util.writeUtf8JSON(response, Util.toJsonStr(returnResult));
			return;
		}
		returnResult.setRtnCode("error");
		//是否是24小时内注册且未激活的用户
		AccountRegister accountRegister = registerService.getAccountRegisterByUsername(accountBean.getUsername(), 24);
		if(accountRegister != null){
			returnResult.setRtnMsg("注册邮箱：" + accountRegister.getEmail() + " 尚未激活，请登录激活！");
			returnResult.setData(accountRegister);
			returnResult.setRtnCode("noActivate");
			Util.writeUtf8JSON(response, Util.toJsonStr(returnResult));
			return;
		}
		returnResult.setRtnMsg( "用户名或密码错误，请重试！");
		Util.writeUtf8JSON(response, Util.toJsonStr(returnResult));
	}
	
	private boolean checkAccountImei(Account account, AccountBean accountBean){
		//网站先不检查
		if(Util.isEmpty(accountBean.getImei(), true)){
			return true;
		}
		//账号已被其他设备使用
		if((!Util.isEmpty(account.getImei(), true)) && (!account.getImei().equals(accountBean.getImei())) && (account.getImeiList().indexOf(accountBean.getImei()) >= 0)){
			return false;
		}
		//更新设备号
		if(!accountBean.getImei().equals(account.getImei())){
			account.setImei(accountBean.getImei());
			String imeiList = account.getImeiList();
			if(imeiList == null){
				imeiList = "";
			}
			if(imeiList.indexOf(accountBean.getImei()) < 0){
				if(imeiList.length() == 0){
					imeiList = accountBean.getImei();
				}else{
					imeiList += "," + accountBean.getImei()  ;
				}
			}
			account.setImeiList(imeiList);
			loginService.saveAccount(account);
		}
		return true;
	}
	/**
	 * 设置session信息
	 * @param session
	 * @param user
	 */
	private void setSession(HttpSession session, Account account, FamilyUser familyUser){
		session.setAttribute("account", account);
		session.setAttribute("familyUser", familyUser);
	}
	/**
	 * 登出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/login/logout.do")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession(true);
		Account account = (Account)session.getAttribute("account");
		if(account != null){
			session.removeAttribute("account");
			session.removeAttribute("familyUser");
		}
		return "login/login";
	}
}
