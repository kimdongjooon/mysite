package com.poscodx.mysite.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.PageVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/{p}", method=RequestMethod.GET)
	public String get_main(
			HttpSession session,
			@PathVariable("p") int page,
			@RequestParam(value = "kwd", required=true, defaultValue="") String kwd,
			Model model) {
		System.out.println("GET: "+page+":"+kwd);
		// 게시판 목록 출력
		List<BoardVo> list = boardService.getBoard(kwd,page);
		model.addAttribute("list",list);
		
		// 세션 등록.
		// 키워드 및 게시판 번호 출력
		session.setAttribute("kwd", kwd);
		PageVo pagevo = boardService.getPage(kwd, page);
		session.setAttribute("pagevo", pagevo);
		
		return "/board/list";
	}
	
	@RequestMapping(value = "/{p}", method=RequestMethod.POST )
	public String post_main(
			HttpSession session,
			@PathVariable("p") int page,
			@RequestParam(value = "kwd", required=true, defaultValue="") String kwd,
			Model model) {
		// 게시판 목록 출력
		System.out.println("POST: "+page+":"+kwd);
		
		List<BoardVo> list = boardService.getBoard(kwd,page);
		model.addAttribute("list",list);
		
		// 세션 등록.
		// 키워드 및 게시판 번호 출력
		session.setAttribute("kwd", kwd);
		System.out.println("POST kwd: "+session.getAttribute("kwd"));
		PageVo pagevo = boardService.getPage(kwd, page);
		session.setAttribute("pagevo", pagevo);
		
		return "redirect:/board/1";
	}
	
	@RequestMapping(value={"/view/{no}/{hit}/{kwd}", "/view/{no}/{hit}", "/view/{no}"}
				, method=RequestMethod.GET)
	public String view(
			@PathVariable("no") int no,
			@PathVariable(value ="hit", required=false) int hit,
			Model model) {

		
		BoardVo boardvo= boardService.getView(no);
		System.out.println(boardvo);
		model.addAttribute("boardvo",boardvo);
		
		return "/board/view";
	}
	
	@RequestMapping(value = "/write", method=RequestMethod.GET )
	public String write() {	
		return "/board/write";
	}
	
	@RequestMapping(value = "/write", method=RequestMethod.POST )
	public String write(
			@RequestParam(value = "mode", required=false) String mode,
			BoardVo boardvo, 
			Model model) {	
		System.out.println("1:"+boardvo);
		// 새글일 때, 
		int g_No;
		int o_No = 1;  // 새글일때 1.
		int depth = 1; // 새글일때 1.
		g_No = boardService.setG_No();
		
		if(mode == null) {
			
		}
		// 현재시간.
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		String currentTimestampToString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTimestamp);
		
		boardvo.setG_no(g_No);
		boardvo.setO_no(o_No); 
		boardvo.setDepth(depth);
		boardvo.setReg_date(currentTimestampToString);
		
		System.out.println("2:"+boardvo);
		boardService.write(boardvo);
		return "redirect:/board/1";
	}
}
