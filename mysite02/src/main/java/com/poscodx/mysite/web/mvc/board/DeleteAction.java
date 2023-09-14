package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;
import java.net.URLEncoder;

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
		
		String kwd = request.getParameter("kwd");
		new BoardDao().deleteBoardByNo(no);
		
		request.setAttribute("kwd", kwd);
		
		PageVo pagevo = (PageVo) request.getSession(true).getAttribute("pagevo");
		response.sendRedirect(request.getContextPath()+"/board?p="+pagevo.getCurrentPage()+"&kwd="+URLEncoder.encode(kwd));
		
			
	}

}
