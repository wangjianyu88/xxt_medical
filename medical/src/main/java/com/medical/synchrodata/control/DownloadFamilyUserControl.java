package com.medical.synchrodata.control;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medical.common.util.Page;
import com.medical.family.entity.FamilyUser;
import com.medical.family.service.FamilyService;
/**
 * 下载家庭成员数据
 * @author 
 * @time 下午12:11:20
 */
@Controller
@Scope("session")
public class DownloadFamilyUserControl extends DownloadBase<FamilyUser>{
	@Autowired
	private FamilyService familyService;
	/**
	 * 子类需要实现的：链接请求入口
	 */
	@RequestMapping("/synchrodata/downloadFamilyUser.do")
	@Override
	public  void synProcess(HttpServletRequest request,HttpServletResponse response){
		super.doDownload(request, response);
	}
	/**
	 * 获取当前页的记录
	 * @return
	 */
	@Override
	public List<FamilyUser> getSynList(String accountId, long startTime, long endTime, Page page){
		return familyService.queryPageFamilyUsersByAccount(accountId, startTime, endTime, page);
	}
	
}
