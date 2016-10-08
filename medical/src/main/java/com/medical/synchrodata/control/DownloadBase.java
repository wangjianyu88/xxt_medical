package com.medical.synchrodata.control;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.medical.common.basedao.BaseDao;
import com.medical.common.util.Page;
import com.medical.common.util.Util;
import com.medical.family.entity.FamilyUser;
import com.medical.login.entity.Account;
import com.medical.synchrodata.bean.Result;
import com.medical.synchrodata.bean.SynDownloadRequest;
import com.medical.synchrodata.bean.SynResponse;


/**
 * 下载的基础类
 * @author 
 * @time 上午9:14:03
 */
public abstract class DownloadBase<T>{
	/*请求头*/
	private HttpServletRequest request;
	/*响应头*/
	private HttpServletResponse response;
	/*当前时间*/
	private long lastUpdateTime;
	/*上传的实体*/
	private SynDownloadRequest synDownloadRequest;
	/*下载的数据结果*/
	private List<T> synList; 
	/*总页数*/
	private int totalPage;
	/*日志*/
	private static final Logger log = LoggerFactory.getLogger(DownloadBase.class);
	@Autowired
    private BaseDao  baseDao;
	/**
	 * 初始化
	 * @param request
	 * @param response
	 * @param synClass
	 * @return
	 */
	private boolean init() {
		//系统当前时间
		Date date = new Date();
		this.lastUpdateTime = date.getTime();
		//上传提交的所有数据
		String synDownloadRequestData = request.getParameter("synDownloadRequest");
		log.debug("synDownloadRequest : " + synDownloadRequestData);
		if(synDownloadRequestData == null || synDownloadRequestData.length() == 0){
			return false;
		}
		//解析上传数据
		this.synDownloadRequest= Util.readValue(synDownloadRequestData, SynDownloadRequest.class);
		if(synDownloadRequest == null ){
			return false;
		}
		return true;
	}
	/**
	 * 下载的主干函数
	 * @param request
	 * @param response
	 */
	public void doDownload(HttpServletRequest request, HttpServletResponse response){
		SynResponse<T> synResponse = null;
		this.request = request;
		this.response = response;
		try{
			if(!this.checkLogin(request)){
				synResponse = getSynResponse(Result.SYN_FAIL, "尚未登陆");
				Util.writeUtf8JSON(this.response, Util.toJsonStr(synResponse));
				return;
			}
			if(!this.init()){
				log.error("请求参数有误");
				synResponse = getSynResponse(Result.SYN_FAIL, "请求参数有误");
				Util.writeUtf8JSON(this.response, Util.toJsonStr(synResponse));
				return;
			}
			//获取每页的记录
			Page page = new Page(synDownloadRequest.getPage(), synDownloadRequest.getPageSize());
			String accountId = synDownloadRequest.getAccountId();
			this.synList = getSynList(accountId, synDownloadRequest.getStartTime(), synDownloadRequest.getEndTime(), page);
			//设置总页数
			setTotalPage(page.getTotal(), synDownloadRequest.getPageSize());
			synResponse = getSynResponse(Result.SYN_SUCCESS, null);
		}catch(Exception e){
			synResponse = getSynResponse(Result.SYN_FAIL, e.getMessage());
			log.error("下载失败：{}", e);
		}finally{
			Util.writeUtf8JSON(this.response, Util.toJsonStr(synResponse));
		}
	}
	/**
	 * 校验是否登陆
	 * @param request
	 * @return true：登陆过 , false : 未登陆过
	 */
	private boolean checkLogin(HttpServletRequest request){
		HttpSession session = request.getSession();
		Account account = (Account)session.getAttribute("account");
		FamilyUser familyUser = (FamilyUser)session.getAttribute("familyUser");
		if(account != null && familyUser != null){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 构造返回给客户端的实体
	 * @param resultStatus
	 * @return
	 */
	private SynResponse<T> getSynResponse(String resultStatus, String resultInfo){
		if(resultStatus.equals(Result.SYN_SUCCESS)){
			resultInfo = "下载成功";
		}
		if(resultInfo == null || resultInfo.length() == 0){
			resultInfo = "下载失败";
		}
		Result result = new Result(resultStatus, resultInfo);
		Map<String, String> synMap = new HashMap<String, String>();
		synMap.put("lastUpdateTime", lastUpdateTime + "");
		synMap.put("totalPage", this.totalPage + "");
		SynResponse<T> synResponse = new SynResponse<T>();
		synResponse.setResult(result);
		synResponse.setSynMap(synMap);
		synResponse.setSynList(this.synList);
		return synResponse;
	}
	/**
	 * 子类需要实现的：链接请求入口
	 */
	public abstract void synProcess(HttpServletRequest request,HttpServletResponse response);
	/**
	 * 获取当前页的记录
	 * @return
	 */
	public abstract List<T> getSynList(String accountId, long startTime, long endTime, Page page);
	/**
	 * 获取分页总数
	 * @return
	 */
	public int getTotalPage() {
		return totalPage;
	}
	/**
	 * 设置分页总数
	 * @param totalPage
	 */
	private void setTotalPage(int synCount, int pageSize){
		if(synCount == 0){
			this.totalPage = 0;
		}
		this.totalPage = synCount / pageSize + 1;
	}
	
}
