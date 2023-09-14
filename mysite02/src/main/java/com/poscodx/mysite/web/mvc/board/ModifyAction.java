package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.web.mvc.Action;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		long no = Long.parseLong(request.getParameter("no"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String kwd = request.getParameter("kwd");
		
		BoardVo boardvo = new BoardVo();
		boardvo.setUser_no(no);
		boardvo.setTitle(title);
		boardvo.setContents(content);
		
		new BoardDao().TitleContentUpdate(boardvo);
		request.setAttribute("kwd", kwd);
		response.sendRedirect(request.getContextPath()+"/board?a=view&no="+no);
		
		

	}

}
