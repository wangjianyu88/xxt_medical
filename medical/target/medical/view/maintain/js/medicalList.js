$(function(){
	var medicalDelete = new MedicalDelete();
	medicalDelete.bindClick();
	
	Pagination.pageInit(_path_ + "/maintain/ajaxMedicalList.do");
});
/**
 * 分页子函数
 */
Pagination.setPageHtml = function(itemList){
	var templateHtml = '';
	for(var i=0; i < itemList.length; i++){
		templateHtml += '<li><span name="medicalBoxSpan" style="display:none">'
			+ '<input type="checkbox" name="medicalBox"  value="' + itemList[i].medicalId +'" ></span>'
			+ '<span class="name">' + itemList[i].name + '</span>'
			+ '<span>' + MedicalExplain(itemList[i]) + '</span>'
			+ '<span class="fright">' + EnumCompute.formateDateTimeForLong(itemList[i].lastUpdateTime) + '&nbsp;&nbsp;&nbsp;&nbsp;'
        	+ '<a href="medicalInfo.do?medicalId=' + itemList[i].medicalId + '">' + $.i18n.prop('page.common.view') + '</a>&nbsp;&nbsp;&nbsp;&nbsp;'
        	+ '<a href="toAddOrEditMedical.do?medicalId=' + itemList[i].medicalId + '">' + $.i18n.prop('page.common.edit') + '</a>'
        	+ '</span></li>';
	}
	$("#medicalUl").html(templateHtml);
}
/**
 * 获取药物说明文字
 * @param medical
 */
function MedicalExplain(medical){
	var medicalExplain = "";
	if(medical == null || medical == undefined){
		return medicalExplain;
	}
	var splits = $.i18n.prop('page.medical.splits', medical.splitDays);
	if(medical.splitDays == 0){
		splits = $.i18n.prop('page.medical.everyday');;
	}
	var eatTime = $.i18n.prop('page.medical.timing.before');
	if(medical.eatTime == 2){
		eatTime = $.i18n.prop('page.medical.timing.after');
	}
	var takeUnit = "";
	switch(medical.takeUnit){
	case 0 : takeUnit = $.i18n.prop('page.medical.unit.grain'); break;
	case 1 : takeUnit = $.i18n.prop('page.medical.unit.slice'); break;
	case 2 : takeUnit = $.i18n.prop('page.medical.unit.bottle'); break;
	case 3 : takeUnit = $.i18n.prop('page.medical.unit.bag'); break;
	case 4 : takeUnit = $.i18n.prop('page.medical.unit.box'); break;
	case 5 : takeUnit = $.i18n.prop('page.medical.unit.gram'); break;
	}
	var startDate = EnumCompute.formateDateForLong(medical.startDate);
	medicalExplain = $.i18n.prop('page.medical.explain', startDate, medical.days, splits, eatTime, medical.takeNum, takeUnit);
	
	return medicalExplain;
}
/**
 * 药物删除
 */
function MedicalDelete(){
	
}
/*绑定事件*/
MedicalDelete.prototype.bindClick = function(){
	$("#batchDeleteBt").click(function(){
		MedicalDelete.preBatchDelete();
	});
	$("#allBox").click(function(){
		MedicalDelete.allCheck();
	});
	$("#deleteBt").click(function(){
		MedicalDelete.toDelete();
	});
	$("#cancelBt").click(function(){
		MedicalDelete.cancelDelete();
	});
}
/*准备批量删除*/
MedicalDelete.preBatchDelete = function(){
	if($("[name='medicalBox']").size() == 0){
		alert($.i18n.prop('page.medical.delMedical.chose'));
		return;
	}
	$("[name='medicalBoxSpan']").show();
	$("#deleteDiv").show();
	$("#initDiv").hide();
}
/*准备删除*/
MedicalDelete.allCheck = function(){
	var checkedValue = false;
	if($("#allBox").attr("checked")){
		checkedValue = true;
	}
	$("[name='medicalBox']").each(function(){
		$(this).attr("checked", checkedValue);
	})
	
}
/*去删除*/
MedicalDelete.toDelete = function(){
	var medicalIds = "";
	$("[name='medicalBox']:checked").each(function(){
		medicalIds += $(this).val() + ",";
	});
	if(medicalIds == ""){
		alert($.i18n.prop('page.medical.delMedical.chose'));
		return;
	}
	medicalIds = medicalIds.substring(0, medicalIds.length - 1);
	var params = {"medicalIds" : medicalIds};
	var url = _path_ + "/maintain/deleteMedical.do"
	$.post(url,params,function(synResponse){
	    if("1" == synResponse.result.resultStatus){
	    	alert($.i18n.prop('page.common.operate.success'));
	    	location.href = _path_ + "/maintain/medicalList.do";
	    }else{
	    	alert($.i18n.prop('page.common.operate.fail'));
	    	$("#deleteBt").show();
	    }
	});
}
/*取消删除*/
MedicalDelete.cancelDelete = function(){
	$("#allBox").attr("checked", false);
	$("[name='medicalBox']").each(function(){
		$(this).attr("checked", false);
	})
	$("[name='medicalBoxSpan']").hide();
	$("#deleteDiv").hide();
	$("#initDiv").show();
}