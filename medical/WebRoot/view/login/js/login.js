$(function(){
	var login = new Login();
	//注册登陆按钮点击事件
	$("#loginBt").click(function(){
		login.toLogin()
	})
	//注册回车登陆
	$("#password").bind("keydown",function(){
		login.keydown();
	});
});
/**
 * 登录对象
 */
function Login(){
}

/*登录校验*/
Login.prototype.checkLogin = function(){
	var username = $.trim($("#username").val());
	$("#username").val(username);
	if(username == ""){
		alert($.i18n.prop('page.login.username'));
		$("#username").focus();
		return false;
	}
	
	var password = $("#password").val();
	if(password == ""){
		alert($.i18n.prop('page.login.password'));
		return false;
	}
	return true;
}
/*登录动作*/
Login.prototype.toLogin = function(){
	$("#loginBt").hide();
	if(!this.checkLogin()){
		$("#loginBt").show();
		return;
	}
	var params = $('#form').serialize();
	$.post(_path_ + "/login/login.do",params,function(result){
	    if("success" == result.rtnCode){
	    	location.href = _path_ + "/login/loginIndex.do";
	    }else if("noActivate" == result.rtnCode){
	    	alert($.i18n.prop('page.login.fail.noActivate'));
	    	$("#loginBt").show();
	    }else{
	    	alert($.i18n.prop('page.login.fail'));
	    	$("#loginBt").show();
	    }
	});
}
/*回车登陆*/
Login.prototype.keydown = function(){
	var e = window.event || arguments.callee.caller.arguments[0];
	if(e && e.keyCode == 13){
		this.toLogin();
	}
}