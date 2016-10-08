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
import com.medical.maintain.entity.MedicalTake;
/**
 * 下载服药记录
 * @author 
 * @time 下午12:21:20
 */
@Controller
@Scope("session")
public class DownloadMedicalTakeControl extends DownloadBase<MedicalTake>{
	@Autowired
	private MedicalService medicalService;
	/**
	 * 子类需要实现的：链接请求入口
	 */
	@RequestMapping("/synchrodata/downloadMedicalTake.do")
	@Override
	public  void synProcess(HttpServletRequest request,HttpServletResponse response){
		super.doDownload(request, response);
	}
	/**
	 * 获取当前页的记录
	 * @return
	 */
	@Override
	public List<MedicalTake> getSynList(String accountId, long startTime, long endTime, Page page){
		return medicalService.queryPageMedicalTakesByAccount(accountId, startTime, endTime, page);
	}
}
