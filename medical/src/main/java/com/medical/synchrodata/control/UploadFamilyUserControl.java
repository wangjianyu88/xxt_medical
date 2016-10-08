package com.medical.synchrodata.control;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medical.family.service.FamilyService;
import com.medical.family.entity.FamilyUser;
/**
 * 上传家庭用户数据
 * @author 
 * @time 下午9:02:43
 */
@Controller
@Scope("session")
public class UploadFamilyUserControl extends UploadBase<FamilyUser>{
	@Autowired
	private FamilyService familyService;
	/**
	 * 子类需要实现的：链接请求入口
	 */
	@RequestMapping("/synchrodata/uploadFamilyUser.do")
	@Override
	public  void synProcess(HttpServletRequest request,HttpServletResponse response){
		super.doUpload(request, response, "com.medical.family.entity.FamilyUser");
	}
	/**
	 * 子类需要实现的：获取每条记录的服务器端的id
	 * @param synNode
	 * @param existidIdList
	 * @return
	 */
	@Override
	public String getNodeId(FamilyUser familyUser){
		return familyUser.getUserId();
	}
	/**
	 * 子类需要实现的：获取每条记录的客户端的id
	 * @param synNode
	 * @return
	 */
	@Override
	public String getClientNodeId(FamilyUser familyUser){
		return familyUser.getClientUserId();
	}
	/**
	 * 新增的数据插入数据库
	 * @param insertList ：新增的数据
	 * @return
	 */
	@Override
	public FamilyUser insertSynData(FamilyUser familyUser){
		familyUser.setLastUpdateTime(super.getLastUpdateTime());
		familyService.saveFamilyUser(familyUser);;
		return familyUser;
	}
	/**
	 * 修改的数据更改数据库
	 * @param updateList ：更改的数据
	 */
	@Override
	public void updateSynData(FamilyUser familyUser){
		familyUser.setLastUpdateTime(super.getLastUpdateTime());
		familyService.updateFamilyUser(familyUser);
	}
}
