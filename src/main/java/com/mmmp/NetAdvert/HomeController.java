package com.mmmp.NetAdvert;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mmmp.NetAdvert.DAO.TestDAO;
import com.mmmp.NetAdvert.DAOImplementation.TestDAOImpl;
import com.mmmp.NetAdvert.model.Customer;
import com.mmmp.NetAdvert.service.AdverService;
import com.mmmp.NetAdvert.service.AdvertServiceImplementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Handles requests for the application home page.
 */
@RestController
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	private AdverService adverService;
	
	@Autowired(required = true)
	@Qualifier(value = "adverService")
	public void setAdverService(AdverService ps) {
		this.adverService = ps;
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	/**
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<String> home(Locale locale, Model model) {
	
		
		adverService.insert(new Customer("asd", 22));
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		return new ResponseEntity<String>("home",HttpStatus.OK);
		
	}
	
}
