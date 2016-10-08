package com.medical.synchrodata.control;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medical.common.util.Util;
import com.medical.family.entity.FamilyUser;
import com.medical.login.entity.Account;
import com.medical.synchrodata.bean.RelationRecord;
import com.medical.synchrodata.bean.SynUploadRequest;
import com.medical.synchrodata.bean.SynResponse;
import com.medical.synchrodata.bean.Result;
/**
 * 上传的基础类
 * @author 
 * @time 上午11:38:56
 */
public abstract  class UploadBase<T>{
	/*请求头*/
	private HttpServletRequest request;
	/*响应头*/
	private HttpServletResponse response;
	/*当前时间*/
	private long lastUpdateTime;
	/*上传的实体*/
	private SynUploadRequest<T> synUploadRequest;
	/*上传的记录*/
	private List<T> synList;
	/*需要修改的记录*/
	private List<T> updateList;
	/*需要新增的记录*/
	private List<T> insertList;
	/*新增记录的客户端和服务器端记录关系*/
	private List<RelationRecord> relationRecordList;
	/*日志*/
	private static final Logger log = LoggerFactory.getLogger(UploadBase.class);
	
	/**
	 * 初始化
	 * @param request
	 * @param response
	 * @param synClass
	 * @return
	 */
	private boolean init(String className) throws ClassNotFoundException{
		//系统当前时间
		Date date = new Date();
		this.lastUpdateTime = date.getTime();
		//上传提交的所有数据
		String synUploadRequestData = request.getParameter("synUploadRequest");
		log.debug("synUploadRequest : " + synUploadRequestData);
		if(synUploadRequestData == null || synUploadRequestData.length() == 0){
			return false;
		}
		Class<?> synClass = Class.forName(className);
		//解析上传数据
		this.synUploadRequest= Util.readValue(synUploadRequestData, SynUploadRequest.class, synClass);
		if(synUploadRequest == null || synUploadRequest.getSynList() == null || synUploadRequest.getSynList().size() == 0){
			return false;
		}
		//获取上传记录
		this.synList = this.synUploadRequest.getSynList();
		return true;
	}
	/**
	 * 上传的主干函数
	 * @param request
	 * @param response
	 * @param synClass
	 * @return
	 */
	public void doUpload(HttpServletRequest request, HttpServletResponse response,String className) {
		this.request = request;
		this.response = response;
		SynResponse<RelationRecord> synResponse = null;
		try{
			if(!this.checkLogin(request)){
				synResponse = getSynResponse(Result.SYN_FAIL, "尚未登陆");
				Util.writeUtf8JSON(this.response, Util.toJsonStr(synResponse));
				return;
			}
			if(!this.init(className)){
				synResponse = getSynResponse(Result.SYN_FAIL, "请求参数有误");
				Util.writeUtf8JSON(this.response, Util.toJsonStr(synResponse));
				return;
			}
			//this.setExistIdList();
			//过滤哪些是新增记录，哪些是需要修改的
			this.filterSynList();
			//保存修改记录
			this.batchUpdateSynData(this.updateList);
			//保存新增记录
			this.insertList = this.batchInsertSynData(this.insertList);
			//设置需要返回的记录关系
			this.setRelationRecordList();
			//响应实体
			synResponse = getSynResponse(Result.SYN_SUCCESS, null);
		}catch(Exception e){
			synResponse = getSynResponse(Result.SYN_FAIL, e.getMessage());
			log.error("上传失败：{}", e);
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
	 * 设置需要返回的记录关系
	 */
	public void setRelationRecordList() {
		this.relationRecordList = new ArrayList<RelationRecord>();
		for(T insertNode : this.insertList){
			RelationRecord relationRecord = new RelationRecord();
			relationRecord.setClientRecordId(this.getClientNodeId(insertNode));
			relationRecord.setServerRecordId(this.getNodeId(insertNode));
			relationRecordList.add(relationRecord);
		}
	}
	/**
	 * 构造返回给客户端的实体
	 * @param resultStatus
	 * @return
	 */
	private SynResponse<RelationRecord> getSynResponse(String resultStatus, String resultInfo){
		if(resultStatus.equals(Result.SYN_SUCCESS)){
			resultInfo = "上传成功";
		}
		if(resultInfo == null || resultInfo.length() == 0){
			resultInfo = "上传失败";
		}
		Result result = new Result(resultStatus, resultInfo);
		Map<String, String> synMap = new HashMap<String, String>();
		synMap.put("lastUpdateTime", lastUpdateTime+"");
		
		SynResponse<RelationRecord> synResponse = new SynResponse<RelationRecord>();
		synResponse.setResult(result);
		synResponse.setSynMap(synMap);
		synResponse.setSynList(this.relationRecordList);
		return synResponse;
	}
	/**
	 * 过滤哪些数据时需要插入的，哪些数据是需要修改的
	 */
	private void filterSynList(){
		this.insertList = new ArrayList<T>();
		this.updateList = new ArrayList<T>();
		for(T synNode : synList){
			String nodeId = getNodeId(synNode);
			if(nodeId != null && nodeId.length() > 0){
				this.updateList.add(synNode);
			}else{
				this.insertList.add(synNode);
			}
		}
	}
	/**
	 * 批量插入
	 * @param insertList
	 * @return
	 */
	private List<T> batchInsertSynData(List<T> insertList){
		List<T> synInsertList = new ArrayList<T>();
		if(insertList == null || insertList.size() == 0){
			return synInsertList;
		}
		for(T insertObject : insertList){
			synInsertList.add(this.insertSynData(insertObject));
		}
		return synInsertList;
		
	}
	/**
	 * 批量修改
	 * @param updateList
	 */
	private void batchUpdateSynData(List<T> updateList){
		if(updateList == null || updateList.size() == 0){
			return;
		}
		for(T updateObject : updateList){
			this.updateSynData(updateObject);
		}
	}
	
	/**
	 * 子类需要实现的：链接请求入口
	 */
	public abstract void synProcess(HttpServletRequest request,HttpServletResponse response);
	/**
	 * 子类需要实现的：获取每条记录的服务器端的id
	 * @param synNode
	 * @param existidIdList
	 * @return
	 */
	public abstract String getNodeId(T synNode);
	/**
	 * 子类需要实现的：获取每条记录的客户端的id
	 * @param synNode
	 * @return
	 */
	public abstract String getClientNodeId(T synNode);
	/**
	 * 新增的数据插入数据库
	 * @param insertList ：新增的数据
	 * @return
	 */
	public abstract T insertSynData(T insertObject);
	/**
	 * 修改的数据更改数据库
	 * @param updateList ：更改的数据
	 */
	public abstract void updateSynData(T updateObject);
	
	public List<T> getUpdateList() {
		return updateList;
	}

	public List<T> getInsertList() {
		return insertList;
	}
	public List<RelationRecord> getRelationRecordList() {
		return relationRecordList;
	}
	public long getLastUpdateTime() {
		return lastUpdateTime;
	}
	
	

	
}
