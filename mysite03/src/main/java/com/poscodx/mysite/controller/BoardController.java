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
	public String getMain(
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
		System.out.println("pagevo[GET]: "+pagevo);
		session.setAttribute("pagevo", pagevo);
		
		return "/board/list";
	}
	
	@RequestMapping(value = "/{p}", method=RequestMethod.POST )
	public String postMain(
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
//		session.setAttribute("kwd", kwd);
		model.addAttribute("kwd",kwd);
		System.out.println("POST kwd: "+ kwd);
		PageVo pagevo = boardService.getPage(kwd, page);
		System.out.println("pagevo[POST]: "+pagevo);
		session.setAttribute("pagevo", pagevo);
		
		return "redirect:/board/1";
	}
	
	@RequestMapping(value={"/view/{no}/{hit}/{kwd}", "/view/{no}/{hit}", "/view/{no}"}
				, method=RequestMethod.GET)
	public String view(
			BoardVo vo,
//			@PathVariable("no") int no,
//			@PathVariable(value ="hit", required=false) int hit,
			Model model) {
		boardService.updateHit(vo);
		
		BoardVo boardvo= boardService.getView(vo.getNo());
		System.out.println("view[GET]: "+boardvo);
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
		
		boardvo.setO_no(1); 
		boardvo.setDepth(1);
		System.out.println("write1[POST]: "+boardvo);
		
		boardvo.setG_no(boardService.setG_No());
	
		System.out.println("2:"+boardvo);
		boardService.write(boardvo);
		return "redirect:/board/1";
	}
	
	@RequestMapping(value = "/review/{no}", method=RequestMethod.GET)
	public String review(
			@PathVariable("no") int no,
			Model model) {
		
		model.addAttribute("mode","review");
		BoardVo boardvo= boardService.getView(no);
		model.addAttribute("boardvo",boardvo);
		
		return "board/view";
	}
	
	@RequestMapping(value="/review/{user_no}", method=RequestMethod.POST)
	public String review(
			BoardVo vo
			) {
		boardService.review(vo);
		
		return "redirect:/board/1";
	}
	
	@RequestMapping(value = "/modify/{no}", method=RequestMethod.GET)
	public String modify(
			@PathVariable("no") int no,
			Model model) {
		
		BoardVo boardvo= boardService.getView(no);
		System.out.println("modify[GET]: "+boardvo);
		model.addAttribute("boardvo",boardvo);

		return  "/board/modify";
	}
	
	@RequestMapping(value = "/modify/{no}", method=RequestMethod.POST)
	public String modify(
			BoardVo vo,
			Model model) {
		boardService.modify(vo);

		return  "redirect:/board/view/"+vo.getNo();
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(
			BoardVo vo) {
		boardService.delete(vo);
		
		return "redirect:/board/1";
	}
	
}
