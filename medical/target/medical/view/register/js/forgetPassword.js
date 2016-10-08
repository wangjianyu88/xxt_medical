$(function(){
	//绑定提交事件
	$("#forgetSubmitBt").click(function(){
		$("#forgetSubmitBt").hide();
		var forgetPassword = new ForgetPassword();
		forgetPassword.doForget();
	})
});
/**
 * 忘记密码对象
 */
function ForgetPassword(){
	
	
}
/*提交*/
ForgetPassword.prototype.doForget = function(){
	if(!this.checkValid()){
		$("#forgetSubmitBt").show();
		return;
	}
	$("#forgetSubmitBt").hide();
	var params = $('#form').serialize();
	$.post(_path_ + "/register/forgetPassword.do",params,function(synResponse){
		if("1" == synResponse.result.resultStatus){
	    	alert($.i18n.prop('page.register.password.reset'));
	    	location.href = _path_ + "/login/loginIndex.do";
	    }else{
	    	alert($.i18n.prop('page.common.operate.fail'));
	    	$("#forgetSubmitBt").show();
	    }
	});
}
/*校验合法性*/
ForgetPassword.prototype.checkValid = function(){
	//用户名非空
	var username = $.trim($("#username").val());
	$("#username").val(username);
	if(username == ""){
		alert($.i18n.prop('page.login.username'));
		$("#username").focus();
		return false;
	}
	//密码非空
	var password = $.trim($("#password").val());
	$("#password").val(password);
	if(password == ""){
		alert($.i18n.prop('page.login.password'));
		$("#password").focus();
		return false;
	}
	//密码非空
	var passwordAgain = $.trim($("#passwordAgain").val());
	$("#passwordAgain").val(passwordAgain);
	if(passwordAgain == ""){
		alert($.i18n.prop('page.register.password.again.null'));
		$("#passwordAgain").focus();
		return false;
	}
	//邮箱非空
	var email = $.trim($("#email").val());
	$("#email").val(email);
	if(email == ""){
		alert($.i18n.prop('page.register.mailbox.null'));
		$("#email").focus();
		return false;
	}
	if(!this.isValidEmail(email)){
		alert($.i18n.prop('page.register.mailbox.valid'));
		$("#email").focus();
		return false;
	}
	if(password != passwordAgain){
		alert($.i18n.prop('page.register.newPassword.different'));
		return false;
	}
	return true;
}
/*邮箱格式判断*/
ForgetPassword.prototype.isValidEmail = function(email){
	var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	 if(!emailReg.test(email)){
		 return false;
	 }
	 return true;
	
}