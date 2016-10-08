$(function(){
	//绑定提交事件
	$("#passowrdSubmitBt").click(function(){
		$("#passowrdSubmitBt").hide();
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
		$("#passowrdSubmitBt").show();
		return;
	}
	$("#passowrdSubmitBt").hide();
	var params = $('#form').serialize();
	$.post(_path_ + "/remind/updatePassword.do",params,function(synResponse){
		if("1" == synResponse.result.resultStatus){
	    	alert($.i18n.prop('page.common.operate.success'));
	    	location.href = _path_ + "/login/loginIndex.do";
	    }else{
	    	alert($.i18n.prop('page.common.operate.fail'));
	    	$("#passowrdSubmitBt").show();
	    }
	});
}
/*校验合法性*/
ForgetPassword.prototype.checkValid = function(){
	
	//密码非空
	var password = $.trim($("#password").val());
	$("#password").val(password);
	if(password == ""){
		alert($.i18n.prop('page.login.password'));
		$("#password").focus();
		return false;
	}
	//新密码非空
	var newPassword = $.trim($("#newPassword").val());
	$("#newPassword").val(newPassword);
	if(newPassword == ""){
		alert($.i18n.prop('page.register.newPassword.null'));
		$("#newPassword").focus();
		return false;
	}
	//再次输入新密码非空
	var newPasswordAgain = $.trim($("#newPasswordAgain").val());
	$("#newPasswordAgain").val(newPasswordAgain);
	if(newPasswordAgain == ""){
		alert($.i18n.prop('page.register.newPassword.again.null'));
		$("#newPasswordAgain").focus();
		return false;
	}
	
	if(newPassword != newPasswordAgain){
		alert($.i18n.prop('page.register.newPassword.different'));
		return false;
	}
	return true;
}
