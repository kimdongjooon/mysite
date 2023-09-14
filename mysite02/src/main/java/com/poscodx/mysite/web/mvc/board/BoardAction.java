package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.PageVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class BoardAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// board db 데이터 가져오기
		// 유저 상관없이 모든 게시글 가져와야함.
		String kwd ="";
		try {
			kwd = request.getParameter("kwd");
			request.setAttribute("kwd", kwd);
//			request.getSession(true).setAttribute("kwd",kwd);
		}catch (Exception ex){
			
		}finally {
		int page = Integer.parseInt(request.getParameter("p"));
		List<BoardVo> list = new BoardDao().boardListFindFiveBoard(kwd,page);
		request.setAttribute("list", list);
//		System.out.println("listsize: "+list.size());
		
		
		// 페이징 하기
		// 규칙: 현재 페이지
		
		PageVo pagevo = new BoardDao().boardPageSet(kwd,page);
//		request.setAttribute("pagevo", pagevo);
		request.getSession(true).setAttribute("pagevo",pagevo);
		
		System.out.println(pagevo);
		request
			.getRequestDispatcher("/WEB-INF/views/board/list.jsp")
			.forward(request, response);
		
		}

	}

}
