package com.poscodx.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

public class SiteInterceptor implements HandlerInterceptor {
	@Autowired
	private SiteService siteService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("SiteInterceptor : 실행. ");
		// 1. handler 종류 확인.
		if(!(handler instanceof HandlerMethod)) {
			// DefaultServletHandler가 처리하는 경우(정적 자원, /assets/**)
			System.out.println("DefaultServletHandler ");
			return true;
		}
		
		
		//3. SiteVo가 없으면 세션 등록.
		HttpSession session = request.getSession();
		SiteVo siteVo = (SiteVo)session.getAttribute("SiteVo");
		
		if(siteVo == null) {
			siteVo = siteService.getSite();
			session.setAttribute("siteVo", siteVo);
			return true;
		}
		
		
		
		return true;
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		// SiteVo가 있을 때 admin에서 update 실행 후 SiteVo 셋팅.
	
		//1. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		
		//2. Handler Method의 @SiteUpdate 가져오기.
		SiteUpdate siteUpdate  = handlerMethod.getMethodAnnotation(SiteUpdate.class);
		
		if(siteUpdate != null) {
			HttpSession session = request.getSession();
			SiteVo siteVo = siteService.getSite();
			session.setAttribute("siteVo", siteVo);
		}
	}
	
	

	
}
