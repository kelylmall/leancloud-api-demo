package com.leancloud.api.web.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.leancloud.api.web.interceptor.TokenAuthorizationInterceptor;
//拦截器处理
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

	public void addInterceptors(InterceptorRegistry registry) {
		TokenAuthorizationInterceptor tokenAuthInter = new TokenAuthorizationInterceptor();
		registry.addInterceptor(tokenAuthInter);
	}
}
