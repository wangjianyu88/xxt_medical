package com.medical.index.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页
 * @author 
 * @time 上午10:00:43
 */
@Controller
@Scope("session")
public class IndexControl {
	/**
	 * 首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index.do")
	public String index(HttpServletRequest request,HttpServletResponse response){
		return "index";
	}
	/**
	 * 关于我们
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/about/aboutUs.do")
	public String aboutUs(HttpServletRequest request,HttpServletResponse response){
		return "about/aboutUs";
	}
	/**
	 * 产品介绍
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/about/productIntroduction.do")
	public String productIntroduction(HttpServletRequest request,HttpServletResponse response){
		return "about/productIntroduction";
	}
}
