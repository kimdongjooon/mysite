package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.PageVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	// 게시물 입력 (insert)
	// 입력 데이터 (게시글 번호, 제목, 내용, 조회수, 입력날짜, 그룹넘버, 그룹안순서, 댓글깊이, 유저번호)
	//          (null, title, contents, hit, reg_date, g_no, o_no, depth, user_no)
	public boolean insert(BoardVo boardvo) {
		// 유저의 세션 authUser에서 user_no가져오기.
		int count = sqlSession.insert("board.insert",boardvo);
		return count == 1;
	}
	
	// g_no 가져오기(select)
	public int setMaxG_no() {
		
		String result = sqlSession.selectOne("board.setMaxG_no");
		if(result == null) {
			result = "0";
		}
		return Integer.parseInt(result) +1;

	}
	
	
	// 게시물 리스트 (select) 리스트형식으로 리턴.
	public List<BoardVo> boardListFindFiveBoard(String kwd, int page) {
		Map<String,Object> map = new HashMap<>();
		map.put("kwd", "%"+kwd+"%");
		map.put("page", (page-1)*5);
	    return sqlSession.selectList("board.boardListFindFiveBoard",map);	
	}
	
	// 게시물 보기 (select)
	// 게시글 검색.
	public BoardVo boardFindByNo(int no) {
		return sqlSession.selectOne("board.boardFindByNo", no);
	}
	
	// 게시물 수정 (update)
	public boolean TitleContentUpdate(BoardVo boardvo) {
		int count = sqlSession.update("board.TitleContentUpdate",boardvo);
		return count == 1;
	}
	
	
	public boolean updateO_NoByG_NoAndO_No(int g_No, int o_No) {
		Map<String,Object> map = new HashMap<>();
		map.put("g_no",g_No);
		map.put("o_no",o_No+1);
		
		int count = sqlSession.update("board.updateO_NoByG_NoAndO_No",map);
		return count ==1;
	}
	
	// 게시물 삭제 (delete)
	public boolean deleteBoardByNo(BoardVo vo) {
		int count = sqlSession.delete("board.deleteBoardByNo",vo);
		return count == 1;		
	}
	
	// 조회수 올리기
	public boolean updateHitByNo(BoardVo vo) {
		int count = sqlSession.update("board.updateHitByNo",vo);
		return count == 1 ;		
	}
		
	// 보드 페이징 처리
	public PageVo boardPageSet(String kwd,int page) {
		int totalBoard = sqlSession.selectOne("board.boardPageSet", "%"+kwd+"%");
		int startPage;
		int endPage;
		int currentPage = page;
		int totalPage=0;
		int outputpage = 5;
		
		PageVo pagevo = new PageVo(currentPage);
		if(kwd == null) {
			kwd="";
		}
		
		// totalpage 삽입.
		if(totalBoard % outputpage !=0) {
			totalPage = totalBoard/outputpage +1;
		}else if(totalBoard==0){
			totalPage = 1;
		}else {
			totalPage = totalBoard/outputpage ;
		}
		
		//startPage : currentPage -2
		// 예외1 : currentPage -2 <= 0 일때 1삽입.
		if((currentPage-2)<=0) {
			startPage = 1;
		}else {
			startPage = currentPage-2;
		}
		// 예외2. currentpage가 totalPage , totalPage-1 일때
		// 여기는 totalPage가 5초과일때. StartPage = totalPage-5 삽입.
		if(currentPage==totalPage || currentPage==totalPage-1) {
			if(totalPage>5) {
				startPage = totalPage-4 ;
			}
			else {
				startPage = 1;
			}
		}
		
		
		// endPage : currentPage +2
		// 예외1. currentPage+2 > totalpage 일경우 endpage에 totalpage삽입.
		if((currentPage+2) > totalPage) {
			endPage = totalPage;
		}else {
			endPage = currentPage+2;
		}
		// 예외2. currentpage가 1또는 2일때 총페이지가 5보다 클때.
		if(currentPage==1 || currentPage==2) {
			if(totalPage>5) {
				endPage = outputpage;
			}
			else {
				endPage = totalPage;
			}
		}

		pagevo.setStartPage(startPage);
		pagevo.setEndPage(endPage);
		pagevo.setNextPage(currentPage+1);
		pagevo.setPrevPage(currentPage-1);
		pagevo.setTotalPage(totalPage);
		pagevo.setTotalBoard(totalBoard);
		
		return pagevo;
	}

	

	

	

	

	

	

	

	

	
}
