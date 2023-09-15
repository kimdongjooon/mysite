package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.GuestBookRepository;
import com.poscodx.mysite.vo.GuestBookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestBookRepository gestbookRepository;
	
	public List<GuestBookVo> getContentsList() {
		return gestbookRepository.findAll();
	}
	
	public Boolean addContents(GuestBookVo vo) {
		return gestbookRepository.insert(vo);
	}
	
	public void deleteContents(int no, String password) {
		gestbookRepository.deleteById(no, password);
	}
	
	
}
