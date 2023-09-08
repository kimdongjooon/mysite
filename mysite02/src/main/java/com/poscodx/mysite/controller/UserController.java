package com.poscodx.mysite.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.UserDao;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.mysite.web.mvc.user.JoinAction;
import com.poscodx.mysite.web.mvc.user.JoinFormAction;
import com.poscodx.mysite.web.mvc.user.JoinSuccessAction;
import com.poscodx.web.mvc.Action;


public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Action action = null;
		String actionName = request.getParameter("a");
		
		if("joinform".equals(actionName)) {
			action = new JoinFormAction();		
		}
		else if("join".equals(actionName)) {
			action = new JoinAction();
		}
		else if("joinsuccess".equals(actionName)) {
			action = new JoinSuccessAction();
		}
		
		if(action == null) {// 사용자를 찾거나 default액션. 
			response.sendRedirect(request.getContextPath());
			return; //함수를 끝내야함. 그래서 리턴. 
		}
		
		action.execute(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
