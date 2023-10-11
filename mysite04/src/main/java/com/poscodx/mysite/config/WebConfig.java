package com.poscodx.mysite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.poscodx.mysite.config.web.FileuploadConfig;
import com.poscodx.mysite.config.web.MessageSourceConfig;
import com.poscodx.mysite.config.web.MvcConfig;
import com.poscodx.mysite.config.web.SecurityConfig;
import com.poscodx.mysite.event.ApplicationContextEventListener;
import com.poscodx.mysite.security.SiteInterceptor;


@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.poscodx.jblog.controller","com.poscodx.jblog.exception"})
@Import({MvcConfig.class, SecurityConfig.class, FileuploadConfig.class, MessageSourceConfig.class})
public class WebConfig implements WebMvcConfigurer{
	// SiteInterceptor
	@Bean
	public HandlerInterceptor siteInterceptor() {
		return new SiteInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(siteInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/assets/**");
	}
	
	// Application ContextEvent Listener
	@Bean
	public ApplicationContextEventListener applicationContextEventListener() {
		return new ApplicationContextEventListener();
	}
	
	
	
}
