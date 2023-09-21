package com.poscodx.mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.mysite.exception.FileUploadServiceException;

@Service
public class FileUploadService {
	private static String SAVE_PATH = "/users/kdj/mysite-uploads";
	private static String URL_PATH = "/images";
	
	public String restore(MultipartFile file) {
		String url = null;
		
		try {
			File uploadDirectory = new File(SAVE_PATH);
			// 존재하지 않으면 디렉토리 생성.
			if(!uploadDirectory.exists()) {
				uploadDirectory.mkdirs();
			}
			// file이 빈 파일 일때. 
			if(file.isEmpty()) {
				return url;
			}
			
			String originFilename = file.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf(".")+1 );
			String saveFilename = generateSaveFilename(extName);
			Long fileSize = file.getSize();
			
			System.out.println("originFilename----> "+originFilename);
			System.out.println("saveFilename----> "+saveFilename);
			System.out.println("fileSize----> "+fileSize);
			
			byte[] data = file.getBytes();
			// 텍스트 이미지 등 모름. 그래서 바이너리 처리위함. 
			OutputStream os = new FileOutputStream(SAVE_PATH+"/"+saveFilename);
			os.write(data);
			os.close();
			
			// resource mapping : url로 들어오는 것을 file system으로 찾아라 
			url = URL_PATH + "/"+ saveFilename;
			
		} catch (IOException ex) {
			throw new FileUploadServiceException(ex.toString());
		}
		
		return url;
	}

	private String generateSaveFilename(String extName) {
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("."+extName);
		
		return filename;
	}

}
