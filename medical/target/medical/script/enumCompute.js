$(function(){
	//加载js的国际化
	_loadI18nProperties_(_language_);
});
/**
 * js国际化默认配置加载
 * @param _language_
 */
function _loadI18nProperties_(language){
	//默认中文
	if(language == undefined || language == ""){
		language = "zh";
	}
	var baseLang = language.substring(0,2).toLowerCase();
	jQuery.i18n.properties({// 加载资浏览器语言对应的资源文件
		 name:'strings', // 资源文件名称
		 path:_path_+'/resources/i18n/', // 资源文件路径
		 mode:'map', // 用 Map 的方式使用资源文件中的值
		 language : baseLang,
		 callback: function() {// 加载成功后设置显示内容
		 } 
	 }); 
} 
/**
 * 药物计算转换
 */
function EnumCompute(){
	
}
/*转换药物单位*/
EnumCompute.takeUnit = function(unitValue){
	var unit = "";
	switch(unitValue){
	case 0 : unit=$.i18n.prop('page.medical.unit.grain'); break;
	case 1 : unit=$.i18n.prop('page.medical.unit.slice'); break;
	case 2 : unit=$.i18n.prop('page.medical.unit.bottle'); break;
	case 3 : unit=$.i18n.prop('page.medical.unit.bag'); break;
	case 4 : unit=$.i18n.prop('page.medical.unit.box'); break;
	case 5 : unit=$.i18n.prop('page.medical.unit.can'); break;
	default : break;
	}
	return unit;
}
/*转换吃饭时间点*/
EnumCompute.eatTime = function(timeValue){
	var eat = "";
	switch(timeValue){
	case 1 : eat=$.i18n.prop('page.medical.timing.before'); break;
	case 2 : eat=$.i18n.prop('page.medical.timing.after'); break;
	default : break;
	}
	return eat;
}
/*转换日期,long 转化为 yyyy-MM-dd*/
EnumCompute.formateDateForLong = function(time){
	time = parseInt(time);
	var date = new Date(time);
	var datetime = date.getFullYear() // "年"
     	+ "-"
     	+ ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1)) // "月"
        + "-"
        + (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()); //日
	return datetime;
}
/*转换日期,long 转化为 yyyy-MM-dd hh24:mi:ss*/
EnumCompute.formateDateTimeForLong = function(time){
	time = parseInt(time);
	var date = new Date(time);
	var datetime = date.getFullYear()  // "年"
	 	+ "-"
	 	+ ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1))  // "月"
	 	+ "-"
	 	+ (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) //日
	 	+ " "
	 	+ ((date.getHours() + 1) > 10 ? (date.getHours() + 1) : "0" + (date.getHours() + 1))  //小时
	 	+ ":"
	 	+ ((date.getMinutes() + 1) > 10 ? (date.getMinutes() + 1) : "0" + (date.getMinutes() + 1))  //分钟
	 	+ ":"
	 	+ ((date.getSeconds() + 1) > 10 ? (date.getSeconds() + 1) : "0" + (date.getSeconds() + 1))  //秒
	return datetime;
}