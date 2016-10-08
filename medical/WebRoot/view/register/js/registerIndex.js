$(function(){
	
});
function RegisterSub(){
	var register = new Register();
	if(!register.checkValid()){
		//校验失败直接返回
		return;
	}
	$("#registerBt").hide();
	//提交注册信息
	register.submitRegister();
}
/**
 * 声明注册对象
 */
function Register(){
	
}

/*注册合法性校验*/
Register.prototype.checkValid = function(){
	var username = $.trim($("#username").val());
	$("#username").val(username);
	if(username == ""){
		alert($.i18n.prop('page.login.username'));
		$("#username").focus();
		return false;
	};
	var password = $("#password").val();
	if(password == ""){
		alert($.i18n.prop('page.login.password'));
		$("#password").focus();
		return false;
	};
	if(password.length<6){
		alert($.i18n.prop('page.register.password.minLength'));
		$("#password").focus();
		return false;
	}
	var age = $.trim($("#age").val());
	$("#age").val(age);
	if(!this.isValidAge(age)){
		alert($.i18n.prop('page.register.age'));
		$("#age").focus();
		return false;
	}
	var email = $.trim($("#email").val());
	$("#email").val(email)
	if(email == ""){
		alert($.i18n.prop('page.register.mailbox.null'));
		$("#email").focus();
		return false;
	};
	if(!this.isValidEmail(email)){
		alert($.i18n.prop('page.register.mailbox.valid'));
		$("#email").focus();
		return false;
	}
	return true;
}
/*邮箱格式判断*/
Register.prototype.isValidEmail = function(email){
	var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	 if(!emailReg.test(email)){
		 return false;
	 }
	 return true;
	
}
/*年龄合法性校验*/
Register.prototype.isValidAge = function(age){
	age = $.trim(age);
	if(age == ""){
		return true;
	}
	//年龄必须为正整数
	var ageReg = /^((1[01]|[1-9])?\d|120)$/; 
	return ageReg.test(age); 
}
/*ajax提交注册请求*/
Register.prototype.submitRegister = function(){
	var params = $('#form').serialize();
	$.post(_path_ + "/register/saveRegister.do",params,function(result){
	    if("success" == result.rtnCode){
	    	alert($.i18n.prop('page.register.success'));
	    	location.href =  _path_ + "/login/loginIndex.do";
	    }else if("usernameRegistered" == result.rtnCode){
	    	alert($.i18n.prop('page.register.usernameRegistered'));
	    	$("#registerBt").show();
	    }else {
	    	alert($.i18n.prop('page.common.operate.fail'));
	    	$("#registerBt").show();
	    }
	});
	
}