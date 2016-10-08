package com.medical.register.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medical.common.bean.ReturnResult;
import com.medical.common.util.BaseControl;
import com.medical.common.util.Util;
import com.medical.register.entity.ActivationCode;
import com.medical.register.service.ActivationService;

/**
 * 验证码激活
 * @author 
 * @time 4:59:44 PM
 */
@Controller
@Scope("session")
public class ActivationControl extends BaseControl{
	@Autowired
	private ActivationService activationService;
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ActivationControl.class);
	/**
	 * 
	 * @param request
	 * @param response
	 * @param codeBean
	 */
	@RequestMapping("/register/verifyCode.do")
	public void verifyCode(HttpServletRequest request,HttpServletResponse response, ActivationCode activationCode){
		ReturnResult returnResult = new ReturnResult();
		//校验用户名是否合法
		if(Util.isEmpty(activationCode.getCode(), true) || Util.isEmpty(activationCode.getImei(), true) ){
			returnResult.setRtnCode("paramError");
			returnResult.setRtnMsg("激活码和设备号不能为空");
			Util.writeUtf8JSON(response, Util.toJsonStr(returnResult));
			return;
		}
		long time = System.currentTimeMillis();
		ActivationCode activationCodeDBA = activationService.getActivationCode(activationCode.getCode());
		if(activationCodeDBA == null || activationCodeDBA.getState() != 1){
			returnResult.setRtnCode("noExist");
			returnResult.setRtnMsg("激活码不存在");
			Util.writeUtf8JSON(response, Util.toJsonStr(returnResult));
			return;
		}
		if((activationCodeDBA.getImei()!=null) &&!activationCode.getImei().equals(activationCodeDBA.getImei())){
			returnResult.setRtnCode("haveBind");
			returnResult.setRtnMsg("激活码已被其他设备绑定");
			Util.writeUtf8JSON(response, Util.toJsonStr(returnResult));
			return;
		}
		if(time < activationCodeDBA.getBeginDate() || time > activationCodeDBA.getEndDate()){
			returnResult.setRtnCode("haveExpired");
			returnResult.setRtnMsg("激活码已过期");
			Util.writeUtf8JSON(response, Util.toJsonStr(returnResult));
			return;
		}
		if(Util.isEmpty(activationCodeDBA.getImei(), true)){
			activationCodeDBA.setImei(activationCode.getImei());
			activationService.updateActivationCode(activationCodeDBA);
		}
		returnResult.setData(activationCodeDBA);
		returnResult.setRtnCode("success");
		returnResult.setRtnMsg("绑定成功");
		Util.writeUtf8JSON(response, Util.toJsonStr(returnResult));
		return;
	}
	

}
