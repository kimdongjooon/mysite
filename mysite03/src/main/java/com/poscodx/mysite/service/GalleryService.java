package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.GalleryRepository;
import com.poscodx.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	
	@Autowired
	GalleryRepository galleryRepository;
	
	public List<GalleryVo> getImages(){
		return galleryRepository.findAllImages();
	}
	
	public void addImages(GalleryVo vo) {
		galleryRepository.updateImage(vo);
	}
	
	public void removeImage(Long no) {
		
	}
	
	

	
}
