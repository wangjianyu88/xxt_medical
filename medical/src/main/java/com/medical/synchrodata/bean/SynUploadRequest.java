package com.medical.synchrodata.bean;
import java.util.Map;
import java.util.List;
/**
 * 同步上传数据的请求头
 * @author 
 * @time 下午2:52:53
 * @param <T>
 */
public class SynUploadRequest<T> {
	/**
	 * 每个上传业务要提交的附属信息
	 */
	private Map<String, String> synMap;
	/**
	 * 要上传的数据
	 */
	private List<T> synList;
	
	public Map<String, String> getSynMap() {
		return synMap;
	}
	public void setSynMap(Map<String, String> synMap) {
		this.synMap = synMap;
	}
	public List<T> getSynList() {
		return synList;
	}
	public void setSynList(List<T> synList) {
		this.synList = synList;
	}

}
