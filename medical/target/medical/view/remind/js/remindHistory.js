$(function(){
	Pagination.pageInit(_path_ + "/remind/ajaxRemindHistory.do");
});

    


/**
 * 分页子函数
 */
Pagination.setPageHtml = function(itemList){
	var templateHtml = '';
	for(var i=0; i < itemList.length; i++){
		templateHtml += '<li><span>' + itemList[i].takeTime + '</span>'
		+ '<span class="name">' + itemList[i].name + '</span>'
		+ $.i18n.prop('page.remind.history.explain', EnumCompute.eatTime(itemList[i].eatTime), itemList[i].takeNum, EnumCompute.takeUnit(itemList[i].takeUnit)) 
		+ '</span></li>';
	}
	$("#medicalUl").html(templateHtml);
}

