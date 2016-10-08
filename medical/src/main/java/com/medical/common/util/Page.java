package com.medical.common.util;
import java.io.Serializable;

/**
 * 存储翻页信息
 */
public class Page implements Serializable { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 4329683329578284885L;

	public static final Integer DEFAULT_PAGE_SIZE = 30;

	private Integer page = 1;// 要获取的页 最小值为1
	private Integer rp = 15; //每页记录数
	

	private Integer total = 0; //总记录数 需要外部设置
	private Integer pageCount = 0; //总页数 设置total时会被设置
	
	public Page(){
		
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public void setRp(Integer rp) {
		this.rp = rp;
	}
	public Page(Integer page, Integer rp) {
		// check:
		if(page<0)
			page = 1;
		if(rp<1)
			rp = 1;
		this.page = page;
		this.rp = rp;
	}

	public Page(Integer pageIndex) {
		this(pageIndex, DEFAULT_PAGE_SIZE);
	}
	public Page(String SpageIndex, String SpageSize) {
		int pageIndex = 1;
		
		int pageSize = Page.DEFAULT_PAGE_SIZE;
		try{
			pageIndex = Integer.parseInt(SpageIndex);
		}catch(Exception e){
			pageIndex = 1;
		}
		try{
			pageSize = Integer.parseInt(SpageSize);
		}catch(Exception e){
		}
		// check:
		if(pageIndex <= 0)
			pageIndex = 1;
		if(pageSize<1)
			pageSize = 1;
		this.page = pageIndex;
		this.rp = pageSize;
	}

	public Integer getRp() { return rp; }

	public Integer getPageCount() { return pageCount; }

	public Integer getTotal() { return total; }

	public Integer getFirstResult() { return (page-1)*rp; }

	public void setTotal(final int i) {
		int totalCount = i;
		if (totalCount<0)
			totalCount = 0;
		this.total = totalCount;
		pageCount = totalCount / rp + (totalCount%rp==0 ? 0 : 1);
		// adjust pageIndex:
		if(totalCount==0) {
			page = 1;
		}else if(page>pageCount){
			page=pageCount;
		}
	}

	public boolean isEmpty() {
		return total==0;
	}

	public boolean isLastPage() {
		if(this.getPageCount()==0)
			return true;
		else
			return this.page == this.getPageCount();
	}

	public boolean isFirstPage() {
		return page<=1;
	}
	
	

}
