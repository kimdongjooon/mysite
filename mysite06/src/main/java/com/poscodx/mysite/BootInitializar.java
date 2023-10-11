package com.poscodx.mysite;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.poscodx.mysite.MySiteApplication;

public class BootInitializar extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {		
		return builder.sources(MySiteApplication.class);
	}
	
}
