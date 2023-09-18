package com.poscodx.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.UserRepository;
import com.poscodx.mysite.vo.UserVo;

@Service
public class UserService {
//	가입 완료 메일 보내기.
//	@Autowired
//	private MailSender mailSender;
	
	@Autowired
	private UserRepository userRepository;

//	public void erpProcess01(UserVo vo) {
//		erpEmployee1Repository.inser(vo);
//		erpEmployee1Repository.inser(vo);
//		erpEmployee1Repository.inser(vo);
//	} ???뭐징.. 
	public void join(UserVo vo) {
		System.out.println(vo);
		userRepository.insert(vo);
		System.out.println(vo);
//		mailSender.send(vo.getEmail(),"","");
	}

	public UserVo getUser(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	public UserVo getUser(Long no) {
		return userRepository.findByNo(no);
	}

	public void update(UserVo userVo) {
		userRepository.update(userVo);
		
	}
}
