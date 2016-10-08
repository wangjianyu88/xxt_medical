/**
 * 初始化
 */
function MedicalInit(medical){
	$("span[name='whichTimeSpan']").each(function(i){
		var value = $(this).text();
		value = value.replace("#", i+1);
		$(this).text(value);
	})
	$("#saveBt").click(function(){
		var medicalSave = new MedicalSave();
		medicalSave.doSave();
	});
	//修改药物数据的加载
	var medicalLoad = new MedicalLoad(medical);
	medicalLoad.doLoad();
	//添加增加提醒时间事件
	var timeRemind = new TimeRemind();
	timeRemind.doRemind();
	MedicalLoad();
}

/**
 * 修改页面的药物数据的加载
 */
function MedicalLoad(medical, medicalTimeList){
	this.medical = medical;
	this.medicalTimeList = medicalTimeList;
}
/*药物数据*/
MedicalLoad.prototype.medical;
/*主干函数*/
MedicalLoad.prototype.doLoad = function(){
	if(this.medical == undefined || this.medical == null || this.medical.medicalId == undefined || this.medical.medicalId == ""){
		return;
	}
	$("#name").val(this.medical.name);
	$("#startDate").val(this.medical.startDate);
	$("#splitDays").val(this.medical.splitDays);
	$("#days").val(this.medical.days);
	$("#takeNum").val(this.medical.takeNum);
	$("#takeUnit").val(this.medical.takeUnit);
	$("[name='eatTime'][value='" + this.medical.eatTime + "']").attr("checked", true);
	$("#deleteBt").show();
}


/**
 * 药品添加
 */
function MedicalSave(){
	
}
/*药物保存*/
MedicalSave.prototype.doSave = function(){
	if(!this.checkValid()){
		return;
	}
	//设置takeTime值
	TimeRemind.setTakeTime();
	$("#saveBt").hide();
	var params = $('#form').serialize();
	var url = _path_ + "/maintain/addMedical.do";
	if($("#medicalId").val() != ""){
		url = _path_ + "/maintain/updateMedical.do";
	}
	$.post(url,params,function(synResponse){
	    if("1" == synResponse.result.resultStatus){
	    	alert($.i18n.prop('page.common.operate.success'));
	    	location.href = _path_ + "/maintain/medicalList.do";
	    }else{
	    	alert($.i18n.prop('page.common.operate.fail'));
	    	$("#saveBt").show();
	    }
	});
}
/*合法性校验*/
MedicalSave.prototype.checkValid = function(){
	var name = $.trim($("#name").val());
	$("#name").val(name);
	if(name == ""){
		alert($.i18n.prop('page.medical.name.null'));
		$("#name").focus();
		return false;
	}
	if(name.length > 15){
		alert($.i18n.prop('page.medical.name.maxLength'));
		name = name.substring(0,15);
		$("#name").val(name);
		$("#name").focus();
		return false;
	}
	if($("#splitDays").find("option:selected").val() == -1){
		alert($.i18n.prop('page.medical.inteval.null'));
		return false;
	}
	if($("#days").find("option:selected").val() == -1){
		alert($.i18n.prop('page.medical.last.null'));
		return false;
	}
	if($("#takeNum").find("option:selected").val() == -1){
		alert($.i18n.prop('page.medical.takeNum.null'));
		return false;
	}
	if($("#takeUnit").find("option:selected").val() == -1){
		alert($.i18n.prop('page.medical.takeUnit.null'));
		return false;
	}
	var remark = $.trim($("#remark").val());
	$("#remark").val(remark);
	if(remark.length > 100){
		alert($.i18n.prop('page.medical.remark.maxLength'));
		name = name.substring(0,100);
		$("#remark").val(name);
		$("#remark").focus();
		return false;
	}
	//校验提示时间是否都填写
	if(!TimeRemind.checkTime()){
		return false;
	}
	return true;
}
/**
 * 提示时间
 */
function TimeRemind(){
	
}

/*提醒的主干函数*/
TimeRemind.prototype.doRemind = function(){
	$("#addTimeBt").click(function(){
		TimeRemind.addTime();
	});
}
/*增加时间*/
TimeRemind.addTime = function(){
	var liModel = '<li name="takeTimeLi" style="padding-left:45px"><span name="whichTimeSpan">第次</span><input name="takeTimeText" medicalTimeId="new" type="text" class="inp"  value=""><span class="fright"><a href="##" onclick="TimeRemind.delTime(this)">' + $.i18n.prop('page.common.delete') + '</a></span></li>';
	$("#takeTimeSpan").append(liModel);
	var whichTimeQuery = $("span[name='whichTimeSpan']");
	whichTimeQuery.last().html($.i18n.prop('page.remind.the') + whichTimeQuery.size() + $.i18n.prop('page.remind.times'));
}
/*删除时间*/
TimeRemind.delTime = function(hrefElement){
	$(hrefElement).parent().parent().remove();
}
/*校验提示时间是否都填写*/
TimeRemind.checkTime = function(){
	var ret = true;
	$("input[name='takeTimeText']").each(function(i){
		var takeNum = i + 1;
		var takeTimeValue = $.trim($(this).val());
		$(this).val(takeTimeValue);
		if(takeTimeValue == ""){
			alert($.i18n.prop('page.medical.remindTime.null',takeNum));
			ret = false;
			return false;
		}
	});
	return ret;
}
/*设置takeTime*/
TimeRemind.setTakeTime = function(){
	var totalTakeTimeValue = "";
	var totalMedicaTimeIdValue = "";
	var totalWhichTimeValue = "";
	$("input[name='takeTimeText']").each(function(i){
		var takeTimeValue = $.trim($(this).val());
		totalTakeTimeValue += takeTimeValue + ",";
		totalWhichTimeValue += (i+1) + ",";
		totalMedicaTimeIdValue += $(this).attr("medicalTimeId") + ",";
	});
	totalTakeTimeValue = totalTakeTimeValue.substring(0, totalTakeTimeValue.length - 1);
	totalWhichTimeValue = totalWhichTimeValue.substring(0, totalWhichTimeValue.length - 1);
	totalMedicaTimeIdValue = totalMedicaTimeIdValue.substring(0, totalMedicaTimeIdValue.length - 1);
	$("#takeTime").val(totalTakeTimeValue);
	$("#medicaTimeIds").val(totalMedicaTimeIdValue);
	$("#whichTimes").val(totalWhichTimeValue);
	
}

