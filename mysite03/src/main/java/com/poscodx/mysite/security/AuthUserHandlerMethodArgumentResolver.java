package com.poscodx.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.poscodx.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(
			MethodParameter parameter, 
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, 
			WebDataBinderFactory binderFactory) throws Exception {
		
		if(!supportsParameter(parameter)) {
			return WebArgumentResolver.UNRESOLVED;
		}
		
//		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
//		HttpSession session = request.getSession();
//		UserVo authUser = (UserVo) session.getAttribute( "authUser" );
//
//		return session.getAttribute("authUser");
		HttpServletRequest httpServletRequest = 
	               webRequest. getNativeRequest( HttpServletRequest.class );
		HttpSession session = httpServletRequest.getSession();
			
		UserVo authUser = (UserVo) session.getAttribute( "authUser" );
		return authUser;

	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		// @AuthUser 가 안붙어 있으면
		if(authUser == null) {
			return false;
		}
		
		// Parameter Type이 UserVo가 아니면, 
		if(parameter.getParameterType().equals(UserVo.class)) {
			return false;
		}
		
		return true;
	}

}
