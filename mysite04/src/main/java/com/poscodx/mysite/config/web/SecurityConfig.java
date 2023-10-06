package com.poscodx.mysite.config.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.poscodx.mysite.security.AuthInterceptor;
import com.poscodx.mysite.security.AuthUserHandlerMethodArgumentResolver;
import com.poscodx.mysite.security.LoginInterceptor;
import com.poscodx.mysite.security.LogoutInterceptor;

@Configuration
@EnableWebMvc
public class SecurityConfig implements WebMvcConfigurer {
	
	//
	// Argument Resolver
	//
	@Bean
	public HandlerMethodArgumentResolver handlerMethodArgumentResolver() {
		return new AuthUserHandlerMethodArgumentResolver();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(handlerMethodArgumentResolver());
		
	}
	
	
	//
	// Interceptors
	//
	
	@Bean
	public HandlerInterceptor LoginInterceptor() {
		return new LoginInterceptor();
	}
	
	@Bean
	public HandlerInterceptor LogoutInterceptor() {
		return new LogoutInterceptor();
	}
	
	@Bean
	public HandlerInterceptor AuthInterceptor() {
		return new AuthInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(LoginInterceptor())
			.addPathPatterns("/user/auth");
		
		registry
			.addInterceptor(LogoutInterceptor())
			.addPathPatterns("/user/logout");
		
		registry
			.addInterceptor(AuthInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/assets/**","/user/auth","/user/logout");
	}
	
	
}
