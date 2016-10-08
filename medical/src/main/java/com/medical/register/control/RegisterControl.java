package com.medical.register.control;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medical.common.util.BaseControl;
import com.medical.common.util.Util;
import com.medical.register.service.RegisterService;
import com.medical.register.entity.AccountRegister;
import com.medical.register.bean.UserRegisterBean;
import com.medical.synchrodata.bean.Result;
import com.medical.synchrodata.bean.SynResponse;
import com.medical.common.bean.ReturnResult;
import com.medical.mail.MailSender;
import com.medical.common.util.EncryptDeciphering;
import com.medical.login.entity.Account;
import com.medical.login.service.LoginService;


/**
 * 注册类
 * @author 
 * @time 下午1:51:28
 */
@Controller
@Scope("session")
public class RegisterControl extends BaseControl{
	@Autowired
	private RegisterService registerService;
	@Autowired
	private LoginService loginService;
	private static final Logger log = LoggerFactory.getLogger(RegisterControl.class);
	@RequestMapping("/register/index.do")
	public String index(HttpServletRequest request,HttpServletResponse response, UserRegisterBean registerUserBean, ModelMap map){
		return "index";
	}
	/**
	 * 注册首页
	 * @param request
	 * @param response
	 * @param map
	 */
	@RequestMapping("/register/registerIndex.do")
	public String registerIndex(HttpServletRequest request,HttpServletResponse response, ModelMap map){
		return "register/registerIndex";
		//Util.writeUtf8Text(response, name);
	}
	/**
	 * 保存注册信息
	 * @param request
	 * @param response
	 * @param registerUserBean
	 */
	@RequestMapping("/register/saveRegister.do")
	public void saveRegister(HttpServletRequest request,HttpServletResponse response, UserRegisterBean userRegisterBean){
		boolean usernameValid = registerService.checkUsernameValid(userRegisterBean.getUsername());
		ReturnResult returnResult = new ReturnResult();
		//校验用户名是否合法
		if(!usernameValid){
			returnResult.setRtnCode("usernameRegistered");
			returnResult.setRtnMsg("用户名已被注册过");
			Util.writeUtf8Text(response, Util.toJsonStr(returnResult));
			return;
		}
		//加密密码
		String encryptPassword = EncryptDeciphering.getInstance().encrypt(userRegisterBean.getPassword());
		userRegisterBean.setPassword(encryptPassword);
		//保存注册信息
		AccountRegister accountRegister = registerService.saveUserRegister(userRegisterBean);
		//发送注册校验邮件
		try{
			
			String languageTag = Util.getLanguageTag(request);
			String encryptAccountId = EncryptDeciphering.getInstance().encrypt(accountRegister.getAccountId());
			String serverUrl = getServerUrl(request);
			MailSender mailSender = new MailSender();
			mailSender.send(userRegisterBean.getEmail(), MailSender.mailSubject, mailSender.getActivateAccountContent(serverUrl, encryptAccountId, languageTag));
		}catch(Exception e){
			registerService.deleteAccountRegister(accountRegister);
			returnResult.setRtnCode("error");
			log.error(e.getMessage());
		}
		//注册成功
		if(returnResult.getRtnCode() == null){
			returnResult.setRtnCode("success");
		}
		Util.writeUtf8JSON(response, Util.toJsonStr(returnResult));
	}
	/**
	 * 激活注册用户
	 * @param request
	 * @param response
	 * @param token
	 * @param map
	 * @return
	 */
	@RequestMapping("/register/activateRegister.do")
	public String activateRegister(HttpServletRequest request,HttpServletResponse response, String token, ModelMap map){
		ReturnResult returnResult = new ReturnResult();
		//解密accountId
		String accountId = EncryptDeciphering.getInstance().decrypt(token);
		AccountRegister accountRegister = registerService.getAccountRegisterById(accountId, 24);
		//无此注册用户
		if(accountRegister == null){
			returnResult.setRtnCode("error");
			returnResult.setRtnMsg("无此注册用户，或超过24小时已失效");
		}else{
			registerService.activateRegister(accountRegister);
			returnResult.setRtnCode("success");
			returnResult.setRtnMsg("激活成功");
		}
		map.put("returnResult", returnResult);
		return "register/registerActivate";
		
	}
	/**
	 * 忘记密码初始页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/register/forgetPasswordIndex.do")
	public String forgetPasswordIndex(HttpServletRequest request,HttpServletResponse response){
		return "register/forgetPassword";
	}
	/**
	 * 忘记密码提交
	 * @param request
	 * @param response
	 * @param account
	 */
	@RequestMapping("/register/forgetPassword.do")
	public void forgetPassword(HttpServletRequest request,HttpServletResponse response, Account account){
		Account oldAccount = registerService.getAccount(account.getUsername(), account.getEmail());
		if(oldAccount == null){
			Util.writeUtf8JSON(response, Util.toJsonStr(super.getSynResponse(Result.SYN_FAIL, "用户名或邮箱不对")));
			return;
		}
		//设置为新密码，同事accountRegister为密码待激活状态
		AccountRegister accountRegister = registerService.getAccountRegisterById(oldAccount.getAccountId(), 24 * 366 * 30);
		//设置的新密码
		String encryptPassword = EncryptDeciphering.getInstance().encrypt(account.getPassword());
		accountRegister.setPassword(encryptPassword);
		accountRegister.setStatus("2");
		accountRegister.setLastUpdatetime(new Date().getTime());
		registerService.updateAccountRegister(accountRegister);
		//发送注册校验邮件
		try{
			String languageTag = Util.getLanguageTag(request);
			String encryptAccountId = EncryptDeciphering.getInstance().encrypt(accountRegister.getAccountId());
			String serverUrl = getServerUrl(request);
			MailSender mailSender = new MailSender();
			mailSender.send(accountRegister.getEmail(), MailSender.mailSubject, mailSender.getForgetPasswordContent(serverUrl, accountRegister.getUsername(), encryptAccountId, languageTag));
		}catch(Exception e){
			log.error(e.getMessage());
			Util.writeUtf8JSON(response, Util.toJsonStr(super.getSynResponse(Result.SYN_FAIL, "操作失败")));
			return;
		}
		Util.writeUtf8JSON(response, Util.toJsonStr(super.getSuccessSynResponse()));
	}
	/**
	 * 点击邮箱链接激活密码重设
	 * @param request
	 * @param response
	 * @param token
	 * @param map
	 * @return
	 */
	@RequestMapping("/register/activateForgetPassword.do")
	public String activateForgetPassword(HttpServletRequest request,HttpServletResponse response,  String token, ModelMap map){
		//解密accountId
		String accountId = EncryptDeciphering.getInstance().decrypt(token);
		AccountRegister accountRegister = registerService.getAccountRegisterById(accountId, 24);
		
		SynResponse<Object> synResponse = null;
		//无此注册用户
		if(accountRegister == null){
			synResponse = super.getSynResponse(Result.SYN_FAIL, "无此注册用户，或超过24小时已失效");
		}else{
			//更新accountRegister为激活状态
			long lastUpdateTime = new Date().getTime();
			accountRegister.setStatus("1");
			accountRegister.setLastUpdatetime(lastUpdateTime);
			registerService.updateAccountRegister(accountRegister);
			//更新account
			Account account = new Account();
			account.setAccountId(accountId);
			account.setAge(accountRegister.getAge());
			account.setEmail(accountRegister.getEmail());
			account.setLastUpdatetime(lastUpdateTime);
			account.setMobile(accountRegister.getMobile());
			account.setName(accountRegister.getName());
			account.setPassword(accountRegister.getPassword());
			account.setSex(accountRegister.getSex());
			account.setUsername(accountRegister.getUsername());
			registerService.updateAccount(account);
			synResponse = super.getSuccessSynResponse();
		}
		map.put("synResponse", synResponse);
		return "register/registerActivate";
	}
	/**
	 * 获取域名+工程名 的地址
	 * @param request
	 * @return
	 */
	private String getServerUrl(HttpServletRequest request){
		StringBuffer url = request.getRequestURL();  
		String serverUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getContextPath()).toString();
		return serverUrl;
	}
	
}
