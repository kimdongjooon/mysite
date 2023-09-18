package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.PageVo;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String mode = null;
		int g_No;
		int o_No = 1;  // 새글일때 1.
		int depth = 1; // 새글일때 1.
		String kwd = "";
		// 사용자의 user no 받기
		UserVo authUser = (UserVo) request.getSession(true).getAttribute("authUser");
		
		
		// 댓글일때 실행. 
		try {
			mode = request.getParameter("mode");
			kwd = request.getParameter("kwd");
		}catch (Exception ex) {
			
		}finally {
			System.out.println(mode+":"+("review".equals(mode)));
		
			if("review".equals(mode)) { // 댓글일때.
				
				g_No = Integer.parseInt(request.getParameter("g_No"));
				o_No = Integer.parseInt(request.getParameter("o_No"))+1;
				depth = Integer.parseInt(request.getParameter("depth"))+1;
				new BoardDao().updateO_NoByG_NoAndO_No(g_No,o_No);
			}
			else { // 새글일때,
				// board db에서 g_no 가져오기. +1하여 다음 새로운 글로 구현.
				g_No = new BoardDao().setMaxG_no() +1 ;
			}
			System.out.println(g_No+":"+o_No+":"+depth);
				
		// 제목 받기
		String title = request.getParameter("title");
		
		// 내용 받기
		String contents = request.getParameter("content");
		
		// 현재시간.
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		String currentTimestampToString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTimestamp);
		
		// 키워드 입력.
		if(kwd == null) {
			kwd = "";
		}
		request.setAttribute("kwd", kwd);
		
		BoardVo boardvo = new BoardVo();
		boardvo.setTitle(title);
		boardvo.setContents(contents);
		boardvo.setHit(0); // 새글의 조회수는 0.
		boardvo.setReg_date(currentTimestampToString);
		boardvo.setG_no(g_No);
		boardvo.setO_no(o_No); 
		boardvo.setDepth(depth); 
		boardvo.setUser_no(authUser.getNo());
		
		new BoardDao().insert(boardvo);
		
		PageVo pagevo = (PageVo) request.getSession(true).getAttribute("pagevo");
		// 게시판으로 돌아가기.
		response.sendRedirect(request.getContextPath()+"/board?p="+ pagevo.getCurrentPage()+"&kwd="+kwd);
		}
	}

}
