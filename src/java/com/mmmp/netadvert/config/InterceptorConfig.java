package com.mmmp.netadvert.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mmmp.netadvert.interceptor.AdvertDeleteInterceptor;

@Configuration
//@EnableWebMvc
public class InterceptorConfig extends WebMvcConfigurerAdapter{
	
	@Bean
	public AdvertDeleteInterceptor advertDeleteInterceptor() {
	    return new AdvertDeleteInterceptor();
	}

	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(advertDeleteInterceptor())
        .addPathPatterns("/api/advert/**")
        .addPathPatterns("/api/comment/**")
        .addPathPatterns("/")
        .addPathPatterns("/api/report/**")
        .addPathPatterns("/api/login")
        .addPathPatterns("/api/user/*/adverts")
        .addPathPatterns("/api/user/*/advert/*/expiredate");
    }
	
}
