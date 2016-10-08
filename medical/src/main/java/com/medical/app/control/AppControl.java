package com.medical.app.control;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medical.common.util.BaseControl;
import com.medical.common.util.Util;
import com.medical.synchrodata.bean.Result;

/**
 * app控制
 * @author 
 * @time 4:57:34 PM
 */
@Controller
@Scope("session")
public class AppControl extends BaseControl{
	private static final Logger log = LoggerFactory.getLogger(AppControl.class);
	/**
	 * 版本更新检测
	 * @param request
	 * @param response
	 * @param currentVersion
	 * @param map
	 */
	@RequestMapping("/app/checkAppVersion.do")
	public void appVersionCheck(HttpServletRequest request,HttpServletResponse response,String os, ModelMap map){
		if(os == null || os.length() == 0){
			os = "android";
		}
		//版本配置文件
		Properties properties = getConfigProperties(os);
		String path = request.getContextPath();
		//跟路径
		String basePath = request.getScheme() + "://"+request.getServerName() + ":"+request.getServerPort() + path;
		try{
			
        	Map<String, String> synMap = new HashMap<String, String>();
        	//apk文件名
        	synMap.put("apkName", properties.getProperty("apkName"));
        	//apkUrl
        	synMap.put("apkUrl", basePath + properties.getProperty("apkUrl"));
        	//app名
        	synMap.put("appName", properties.getProperty("appName"));
        	//changeLog
        	synMap.put("changeLog", properties.getProperty("changeLog"));
        	//versionCode
        	synMap.put("versionCode", properties.getProperty("versionCode"));
        	//versionName
        	synMap.put("versionName", properties.getProperty("versionName"));
        	//forceUpdate
        	synMap.put("forceUpdate", properties.getProperty("forceUpdate"));
        	//status
        	synMap.put("status", properties.getProperty("status"));
        	//createDate
        	synMap.put("createDate", properties.getProperty("createDate"));
        	Util.writeUtf8JSON(response, Util.toJsonStr(super.getSynResponse(Result.SYN_SUCCESS, "调用成功", synMap, null)));
	        
		}catch(Exception e){
			log.error(e.getMessage());
			Util.writeUtf8JSON(response, Util.toJsonStr(super.getSynResponse(Result.SYN_FAIL, "服务器异常，请重试")));
		}
	}
	/**
	 * 加载配置文件
	 * @return
	 */
	private Properties getConfigProperties(String os){
		Properties properties = new Properties(); 
		
		try {  
			properties.load(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(os + "_version.properties"), "UTF-8"));
		} catch (IOException e) {   
			  e.printStackTrace();   
		}
		return properties;
	}
}
