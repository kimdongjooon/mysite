package com.poscodx.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.mysite.service.FileUploadService;
import com.poscodx.mysite.service.GalleryService;
import com.poscodx.mysite.vo.GalleryVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping("")
	public String index(
			Model model
			) {
		
		List<GalleryVo> list = galleryService.getImages();
		model.addAttribute("list",list);
		
		return "gallery/index";
	}
	
	@RequestMapping("/upload")
	public String main(
			GalleryVo galleryvo,
			@RequestParam("file") MultipartFile file
			) {
		
		// 이미지 url 셋팅
		String url = fileUploadService.restore(file);
		galleryvo.setImage_url(url);
		

		galleryService.addImages(galleryvo);
		
		return "redirect:/gallery";
		
	}
	
	@RequestMapping("/delete")
	public String delete() {
		
		return "redirect:/gallery"; 
	}
	
	
	
}
