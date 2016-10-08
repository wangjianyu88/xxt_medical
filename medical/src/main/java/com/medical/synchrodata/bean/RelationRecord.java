package com.medical.synchrodata.bean;
/**
 * 上传后，返回的客户端的服务端id与客户端id记录的对应关系
 * @author 
 * @time 下午1:14:07
 */
public class RelationRecord {
	/*客户端记录id*/
	private String clientRecordId;
	/*服务器端记录id*/
	private String serverRecordId;
	public String getClientRecordId() {
		return clientRecordId;
	}
	public void setClientRecordId(String clientRecordId) {
		this.clientRecordId = clientRecordId;
	}
	public String getServerRecordId() {
		return serverRecordId;
	}
	public void setServerRecordId(String serverRecordId) {
		this.serverRecordId = serverRecordId;
	}

}
