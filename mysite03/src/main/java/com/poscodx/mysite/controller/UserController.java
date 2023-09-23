package com.poscodx.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.AuthUser;
import com.poscodx.mysite.service.UserService;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	// 회원가입 페이지.
	@RequestMapping(value = "/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}
	
	@RequestMapping(value = "/join", method=RequestMethod.POST)
	public String join(
			@ModelAttribute @Valid UserVo userVo, 
			BindingResult result, 
			Model model) {
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
//			for(ObjectError error : list) {
//				System.out.println("error: "+error);
//			}
			
			model.addAllAttributes(result.getModel());
			
			return "user/join";
		}
		System.out.println(userVo);
		
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping(value = "/joinsuccess", method=RequestMethod.GET)
	public String joinsuccess(@Validated UserVo userVo) {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value = "/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
//	@RequestMapping(value = "/auth", method=RequestMethod.POST)
//	public String auth(
//			HttpSession session,
//			@RequestParam(value="email",required=true, defaultValue="")  String email,
//			@RequestParam(value="password",required=true, defaultValue="")  String password,
//			Model model) {
//		
//		// Repsitory에서 변수를 보내어 직접 작업하는것은 이식성이 떨어짐. 안좋음.
//		UserVo authUser = userService.getUser(email,password);
//		if(authUser == null) { 
//			model.addAttribute("email",email); // 틀렸을때 작성한 이메일 유지하게 하기.
//			return "user/login";
//		}
//		// 인증 처리 (세션처리)
//		session.setAttribute("authUser", authUser);
//		return "redirect:/";
//	}
	
//	@RequestMapping("/logout")
//	public String logout(HttpSession session) {
//		session.removeAttribute("authUser");
//		session.invalidate();		
//		return "redirect:/";
//	}
	
	@Auth
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) {
		System.out.println("authUser: "+authUser);
		UserVo userVo = userService.getUser(authUser.getNo());
		System.out.println("userVo: "+userVo);
		model.addAttribute("userVo",userVo);
		return "user/update";
	}
	
	@Auth
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(HttpSession session,UserVo userVo) {

		UserVo authUser = (UserVo) session.getAttribute("authUser");
	
		userVo.setNo(authUser.getNo());
		userService.update(userVo);
		
		// 성공시.
		authUser.setName(userVo.getName());
		
		return "redirect:/user/update";
	}
	
//	@ExceptionHandler(Exception.class)
//	public String handlerException() {
//		return "error/exception";
//	}
	
}
