package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.web.mvc.Action;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String kwd = request.getParameter("kwd");
		
		int no = Integer.parseInt(request.getParameter("no"));
		
		BoardVo boardvo = new BoardDao().boardFindByNo(no);
		request.setAttribute("boardvo", boardvo);
		request.setAttribute("kwd", kwd);
		request
			.getRequestDispatcher("/WEB-INF/views/board/modifyform.jsp")
			.forward(request, response);

	}

}
