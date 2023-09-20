package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.PageVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	public List<BoardVo> getBoard(String kwd, int page) {
		return boardRepository.boardListFindFiveBoard(kwd, page);
	}

	public PageVo getPage(String kwd, int page) {
		return boardRepository.boardPageSet(kwd, page);
		
	}

	public BoardVo getView(int no) {
		return boardRepository.boardFindByNo(no);
	}

	public void write(BoardVo boardvo) {
		boardRepository.insert(boardvo);
		
	}

	public int setG_No() {
		return boardRepository.setMaxG_no();
	}

	public void updateHit(BoardVo vo) {
		boardRepository.updateHitByNo(vo);
		
	}

	public void modify(BoardVo vo) {
		boardRepository.TitleContentUpdate(vo);
		
	}

	public void review(BoardVo vo) {
		
		boardRepository.updateO_NoByG_NoAndO_No(vo.getG_no(),vo.getO_no());
		vo.setO_no(vo.getO_no()+1);
		vo.setDepth(vo.getDepth()+1);
		boardRepository.insert(vo);
	}

	public void delete(BoardVo vo) {
		boardRepository.deleteBoardByNo(vo);
		
	}
	
	
	
	
	
}
