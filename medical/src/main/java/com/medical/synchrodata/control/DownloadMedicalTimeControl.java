package com.medical.synchrodata.control;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medical.common.util.Page;
import com.medical.maintain.entity.MedicalTime;
import com.medical.maintain.service.MedicalService;
/**
 * 下载服药时间
 * @author 
 * @time 下午1:10:50
 */
@Controller
@Scope("session")
public class DownloadMedicalTimeControl extends DownloadBase<MedicalTime>{
	@Autowired
	private MedicalService medicalService;
	/**
	 * 子类需要实现的：链接请求入口
	 */
	@RequestMapping("/synchrodata/downloadMedicalTime.do")
	@Override
	public  void synProcess(HttpServletRequest request,HttpServletResponse response){
		super.doDownload(request, response);
	}
	/**
	 * 获取当前页的记录
	 * @return
	 */
	@Override
	public List<MedicalTime> getSynList(String accountId, long startTime, long endTime, Page page){
		return medicalService.queryPageMedicalTimesByAccount(accountId, startTime, endTime, page);
	}
}
