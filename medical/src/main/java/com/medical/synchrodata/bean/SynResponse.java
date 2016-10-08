package com.medical.synchrodata.bean;
import java.util.*;
/**
 * 上传或下载的响应类
 * @author 
 * @time 下午2:44:02
 * @param <T>
 */
public class SynResponse<T> {
	/**
	 * 操作结果
	 */
	private Result result;
	/**
	 * 每个业务的附属信息
	 */
	private Map<String, String> synMap;
	/**
	 * 要处理的数据列表
	 */
	private List<T> synList;
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
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
