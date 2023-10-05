package com.poscodx.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.poscodx.mysite.vo.GuestBookVo;

@Repository
public class GuestBookRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 입력 게시글 insert
	public boolean insert(GuestBookVo vo) {
		int count = sqlSession.insert("guestbook.insert",vo);
		return count == 1;
	}

	// 게시글 모든글 출력 findAll - 리스트반환.
	public List<GuestBookVo> findAll() {
		
		
		List<GuestBookVo> result= sqlSession.selectList("guestbook.findAll");
		
		
		
		return result;
		
	}
	
	// no 로 접근하여 특정 게시글 삭제 delete
	public Boolean deleteByNoAndPassword(int no, String password) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("password",password);
		int count = sqlSession.delete("guestbook.deleteByNoAndPassword", map);
		return count==1;
	}
	
	

}
