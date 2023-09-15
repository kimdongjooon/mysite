package com.poscodx.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.GuestbookService;
import com.poscodx.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	
	@RequestMapping("")
	public String main(Model model) {
		List<GuestBookVo> list = guestbookService.getContentsList();
		model.addAttribute("list",list);
		
		return "/guestbook/main";
	}
	
	@RequestMapping(value ="/add", method=RequestMethod.POST)
	public String add(GuestBookVo vo){
		guestbookService.addContents(vo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping("/deleteform/{no}")
	public String deleteform(@PathVariable("no") int no,Model model) {
		model.addAttribute("no",no);
		return "/guestbook/deleteform";
		
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(
			@PathVariable("no") int no, 
			@RequestParam("password") String password) {
		guestbookService.deleteContents(no, password);
		return "redirect:/guestbook";
		
		
	}
	
	
}
