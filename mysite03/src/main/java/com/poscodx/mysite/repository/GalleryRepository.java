package com.poscodx.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.GalleryVo;

@Repository
public class GalleryRepository {
	
	@Autowired
	SqlSession sqlSession;
		
	public List<GalleryVo> findAllImages() {
		
		return sqlSession.selectList("gallery.findAllImages");
	}

	public void updateImage(GalleryVo vo) {
		sqlSession.insert("gallery.updateImage",vo);
	}
	
}
