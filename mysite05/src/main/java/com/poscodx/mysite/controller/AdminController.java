package com.poscodx.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.SiteUpdate;
import com.poscodx.mysite.service.FileUploadService;
import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

@Auth(Role ="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	// 스프링 컨테이너 
	@Autowired
	private ApplicationContext applicationContext;
	
	// 서블릿 컨테이너. 
	// 데이터 업데이트시 servletContext를변경하기 위함. 다른 페이지에서는 servletContext사용해서 로고데이터 가져옴.
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	
	@RequestMapping(value = "", method=RequestMethod.GET)
	public String main(Model model) {
		SiteVo siteVo = siteService.getSite();
		model.addAttribute("siteVo",siteVo);
		return "admin/main";
	}
	
	@SiteUpdate
	@RequestMapping(value = "/main/update/{no}", method=RequestMethod.POST)
	public String main(
			@RequestParam("new_profile") MultipartFile file,
			SiteVo sitevo) {
		System.out.println(sitevo);
		
		
		// 이미지 url 셋팅
		String url = fileUploadService.restore(file);
		sitevo.setProfile(url);
		

		siteService.updateSite(sitevo);
		
		SiteVo site = applicationContext.getBean(SiteVo.class);
		
		// 성공할때 servletContext에서 sitevo를 바꿔줘야함.		
		servletContext.setAttribute("siteVo", sitevo);
		
//		site.setTitle(sitevo.getTitle());
//		site.setWelcome(sitevo.getWelcome());
//		site.setProfile(sitevo.getProfile());
//		site.setDescription(sitevo.getDescription());
		// 위와 같음.
		BeanUtils.copyProperties(sitevo, site);
		
		return "redirect:/admin";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}

}
