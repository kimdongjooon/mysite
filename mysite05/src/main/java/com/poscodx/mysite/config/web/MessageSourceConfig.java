package com.poscodx.mysite.config.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration 
public class MessageSourceConfig {
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("com/poscodx/mysite/config/web/messages/messages_ko","com/poscodx/mysite/config/web/messages/messages_en");
		messageSource.setDefaultEncoding("utf-8"); // 디폴트값 : utf-8
		return messageSource;
	}
}
