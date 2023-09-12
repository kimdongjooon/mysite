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

public class BoardAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// board db 데이터 가져오기
		// 유저 상관없이 모든 게시글 가져와야함.
		List<BoardVo> list = new BoardDao().boardListFindAll();
		request.setAttribute("list", list);
		
		request
			.getRequestDispatcher("/WEB-INF/views/board/list.jsp")
			.forward(request, response);
		

	}

}
