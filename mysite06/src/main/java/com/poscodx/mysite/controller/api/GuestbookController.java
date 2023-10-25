package com.poscodx.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poscodx.mysite.dto.JsonResult;
import com.poscodx.mysite.service.GuestbookService;
import com.poscodx.mysite.vo.GuestbookVo;



@RestController("GuestbookApiController")
@RequestMapping("/api/guestbook")
public class GuestbookController {
	
	@Autowired
	GuestbookService guestbookService;
	
	@GetMapping
	public JsonResult get() {
		List<GuestbookVo> list = guestbookService.getContentsList();
	
		return JsonResult.success(list);
	}
	
	@PostMapping
	public JsonResult post(
			@RequestBody GuestbookVo vo
			) {
		guestbookService.addContents(vo);
		return JsonResult.success(vo);
		
	}
}
