package com.poscodx.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.mysite.service.UserService;
import com.poscodx.mysite.vo.UserVo;

public class LoginInterceptor implements HandlerInterceptor {
	// new로 객체를 생성하는 것은 위험합니다... 주의!!
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
//		UserService userService = new UserService(); 이렇게 쓰면 안좋음.
		UserVo authUser = userService.getUser(email,password);
		
		if(authUser == null) {
			request.setAttribute("email", email);
			request
				.getRequestDispatcher("/WER-INF/views/user/login.jsp")
				.forward(request, response);
			
			return false;
		}
		
		System.out.println("auth success: "+ authUser);
		
		HttpSession session =request.getSession(true); 
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath());
		
		return false;
	}
	
}
