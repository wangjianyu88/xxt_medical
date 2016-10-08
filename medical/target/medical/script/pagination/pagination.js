/**
 * 分页对象
 */
function Pagination(){
	
}
/*分页的公用配置*/
Pagination.commonOption = {
	 callback: pageCallback,// 为翻页调用次函数。
     prev_text : "上一页",
     next_text : "下一页",
     items_per_page : 15, //每页的数据个数
     num_display_entries : 3, //两侧首尾分页条目数
     current_page : 0,   //当前页码,jquery端从0开始
     num_edge_entries : 1, //连续分页主体部分分页条目数
     url : "",
}
Pagination.pageBean;

Pagination.pageInit = function(url){
	Pagination.commonOption.prev_text = $.i18n.prop('page.pagination.back');
	Pagination.commonOption.next_text = $.i18n.prop('page.pagination.next');
	var params = {"page" : Pagination.commonOption.current_page, "rp" : Pagination.commonOption.items_per_page};
	Pagination.commonOption.url = url;
	$.post(Pagination.commonOption.url,params,function(pageBean){
		Pagination.pageBean = pageBean;
		Pagination.setPageHtml(pageBean.itemList);
		$("#Pagination").pagination(pageBean.total, Pagination.commonOption);
	});
}
/**
 * 点击页码后的回调响应函数
 * page : 点击的当前页码
 * jq : Pagination所在的jquery对象
 */
function pageCallback(page, jq){
	if(Pagination.pageBean != undefined && Pagination.pageBean != null){
		Pagination.pageBean = undefined;
		return;
	}
	Pagination.commonOption.current_page = page + 1;
	var params = {"page" : Pagination.commonOption.current_page, "rp" : Pagination.commonOption.items_per_page};
	$.post(Pagination.commonOption.url, params, function(pageBean){
		//调用Pagination.setPageHtml子函数刷新记录区域
		Pagination.setPageHtml(pageBean.itemList);
	});
	
};
