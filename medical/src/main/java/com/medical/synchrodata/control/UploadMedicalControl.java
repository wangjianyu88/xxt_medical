package com.medical.synchrodata.control;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.medical.maintain.entity.Medical;
import com.medical.maintain.service.MedicalService;
/**
 * 上传药物数据
 * @author 
 * @time 下午9:02:43
 */
@Controller
@Scope("session")
public class UploadMedicalControl extends UploadBase<Medical>{
	@Autowired
	private MedicalService medicalService;
	/**
	 * 子类需要实现的：链接请求入口
	 */
	@RequestMapping("/synchrodata/uploadMedical.do")
	@Override
	public  void synProcess(HttpServletRequest request,HttpServletResponse response){
		super.doUpload(request, response, "com.medical.maintain.entity.Medical");
	}
	/**
	 * 子类需要实现的：获取每条记录的服务器端的id
	 * @param synNode
	 * @param existidIdList
	 * @return
	 */
	@Override
	public String getNodeId(Medical medical){
		return medical.getMedicalId();
	}
	/**
	 * 子类需要实现的：获取每条记录的客户端的id
	 * @param synNode
	 * @return
	 */
	@Override
	public String getClientNodeId(Medical medical){
		return medical.getClientMedicalId();
	}
	/**
	 * 新增的数据插入数据库
	 * @param insertList ：新增的数据
	 * @return
	 */
	@Override
	public Medical insertSynData(Medical medical){
		medical.setLastUpdateTime(super.getLastUpdateTime());
		medicalService.saveMedical(medical);
		return medical;
	}
	/**
	 * 修改的数据更改数据库
	 * @param updateList ：更改的数据
	 */
	@Override
	public void updateSynData(Medical medical){
		medical.setLastUpdateTime(super.getLastUpdateTime());
		medicalService.updateMedical(medical);
	}
}
