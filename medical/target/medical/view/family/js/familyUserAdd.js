$(function(){
	$("[name='userId'][status='2']").attr("checked", true);
	//保存事件
	$("#saveBt").click(function(){
		var familyUserAdd = new FamilyUserAdd();
		familyUserAdd.doAdd();
	})
	
});
/**
 * 家庭成员管理
 */
function FamilyUserAdd(){
	
}
/*成员添加*/
FamilyUserAdd.prototype.doAdd = function(){
	//合法性校验
	if(!this.checkValid()){
		return;
	}
	var url = _path_ + "/family/familyUserAddSubmit.do";
	$("#saveBt").hide();
	var params = $('#form').serialize();
	$.post(url,params,function(synResponse){
	    if("1" == synResponse.result.resultStatus){
	    	alert($.i18n.prop('page.common.operate.success'));
	    	location.href = _path_ + "/family/familyUserList.do";
	    }else{
	    	alert($.i18n.prop('page.common.operate.fail'));
	    	$("#saveBt").show();
	    }
	});
}
/*校验合法性*/
FamilyUserAdd.prototype.checkValid = function(){
	var name = $.trim($("#name").val());
	$("#name").val(name);
	if(name == ""){
		alert($.i18n.prop('page.family.name.null'))
		$("#name").focus();
		return false;
	}
	if(name.length > 15){
		alert($.i18n.prop('page.family.name.maxLength'));
		$("#name").focus();
		return false;
	}
	return true;
}