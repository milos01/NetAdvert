package com.mmmp.netadvert.interceptor;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mmmp.netadvert.model.Advert;
import com.mmmp.netadvert.service.AdverService;

public class AdvertDeleteInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private AdverService adverService;

	@Autowired(required = true)
	public void setAdverService(AdverService adverService) {
		this.adverService = adverService;
	}
	
	 public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
	            Object handler) throws Exception {
		 
		 List<Advert> adverts = this.adverService.allAdverts();
		 Date d = new Date(new java.util.Date().getYear(), new java.util.Date().getMonth(), new java.util.Date().getDate());
		 for(Advert a : adverts){
			 if(a.getExpire_date().compareTo(d)<0 && a.getDeleted()==false){
				 a.setDeleted(true);
				 this.adverService.updateAdvert(a);
			 }
		 }
		 return true;
	 }

}
