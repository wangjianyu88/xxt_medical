package com.medical.synchrodata.control;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medical.common.util.Page;
import com.medical.maintain.service.MedicalService;
import com.medical.maintain.entity.Medical;
/**
 * 下载药物数据
 * @author 
 * @time 下午12:11:20
 */
@Controller
@Scope("session")
public class DownloadMedicalControl extends DownloadBase<Medical>{
	@Autowired
	private MedicalService medicalService;
	/**
	 * 子类需要实现的：链接请求入口
	 */
	@RequestMapping("/synchrodata/downloadMedical.do")
	@Override
	public  void synProcess(HttpServletRequest request,HttpServletResponse response){
		super.doDownload(request, response);
	}
	/**
	 * 获取当前页的记录
	 * @return
	 */
	@Override
	public List<Medical> getSynList(String accountId, long startTime, long endTime, Page page){
		return medicalService.queryPageMedicalsByAccount(accountId, startTime, endTime, page);
	}
}
