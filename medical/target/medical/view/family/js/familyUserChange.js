$(function(){
	$("[name='userId'][status='2']").attr("checked", true);
	//保存事件
	$("#saveBt").click(function(){
		var familyUserChange = new FamilyUserChange();
		familyUserChange.doChange();
	})
	
});
/**
 * 家庭成员管理
 */
function FamilyUserChange(){
	
}
/*成员切换*/
FamilyUserChange.prototype.doChange = function(){
	var url = _path_ + "/family/familyUserChangeSubmit.do";
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