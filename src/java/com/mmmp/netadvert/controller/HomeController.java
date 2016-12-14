package com.mmmp.netadvert.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mmmp.netadvert.service.AdverService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	private AdverService adverService;
	
	@Autowired(required = true)
	public void setAdverService(AdverService ps) {
		this.adverService = ps;
	}

	/**
	 * This method will redirect user on index.html, otherwise will respond with 404 error response.
	 * @return index.html
     */
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "text/html")
	public String home() {

		return "core/index.html";

	}
	
}
