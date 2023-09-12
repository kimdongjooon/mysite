package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int no = Integer.parseInt(request.getParameter("no"));
		
			
//		List<BoardVo> list = new BoardDao().boardListFindAll();
		BoardVo boardvo = new BoardDao().boardFindByNo(no);
		request.setAttribute("boardvo", boardvo);
		
		request
			.getRequestDispatcher("/WEB-INF/views/board/view.jsp")
			.forward(request, response);
	
		
		
//		WebUtil.forward("board/view",request,response);

	}

}
