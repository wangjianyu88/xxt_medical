package com.medical.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.medical.family.entity.FamilyUser;
import com.medical.login.entity.Account;
/**
 * 登录filter
 * @author 
 * @time 下午1:16:08
 */
public class LoginFilter implements Filter{
	private static String[] loginUrls = {"/maintain/", "/remind/",  "/family/"}; 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		HttpSession session = httpRequest.getSession();
		//请求的url
		String url = httpRequest.getRequestURI();
		//登录的账号和用户
		Account account = (Account)session.getAttribute("account");
		FamilyUser familyUser = (FamilyUser)session.getAttribute("familyUser");
		//登录过，或不需要登录校验
		if((account != null && familyUser != null) || !isLoginUrl(url)){
			chain.doFilter(request, response);
			return;
		}
		String path = httpRequest.getContextPath();
		//没登陆的跳转到登录页
		httpResponse.sendRedirect(path + "/login/loginIndex.do");
	}
	/**
	 * 是否是需要登录校验的url
	 * @param url
	 * @return
	 */
	private boolean isLoginUrl(String url){
		for(String loginUrl : loginUrls){
			if(url.indexOf(loginUrl) >=0){
				return true;
			}
		}
		return false;
	}
	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}
	@Override
	public void destroy() {
		
	}

}
