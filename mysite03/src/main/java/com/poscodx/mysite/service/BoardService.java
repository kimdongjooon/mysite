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
	
	
	
	
	
}
