package com.poscodx.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.UserDao;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class UpdateformAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// Access Control (접근제어) 보안, 인증체크 
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
//			response.sendRedirect(request.getContextPath()+"/user?a=loginform");
			response.sendRedirect(request.getContextPath());
		}
		////////////////////////////////////////////////////////////////////////////
		
		// 성별 체킹 함수 세션 등록
		authUser.setGender(new UserDao().updateformSetGender(authUser));
		request.getSession(true).setAttribute("authUser", authUser);
		
		WebUtil.forward("user/updateform", request, response);
		
		
	}

}
