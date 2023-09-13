package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.PageVo;
import com.poscodx.web.mvc.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int no = Integer.parseInt(request.getParameter("no"));
		
		new BoardDao().deleteBoardByNo(no);
		
		PageVo pagevo = (PageVo) request.getSession(true).getAttribute("pagevo");
		response.sendRedirect(request.getContextPath()+"/board?p="+pagevo.getCurrentPage());
		
			
	}

}
