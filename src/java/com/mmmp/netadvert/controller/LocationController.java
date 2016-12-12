package com.mmmp.netadvert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmmp.netadvert.model.Location;
import com.mmmp.netadvert.service.AdverService;

@RestController
@RequestMapping(value="api/location")
public class LocationController {

	
	@Autowired
	private AdverService adverService;
	
	@Autowired(required = true)
	public void setAdverService(AdverService ps) {
		this.adverService = ps;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Location> newLocation(Location location){
		this.adverService.createLocation(location);
		return new ResponseEntity<Location>(location,HttpStatus.OK);
		
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Location> updateLocation(Location location){
		this.adverService.updateLocation(location);
		return new ResponseEntity<Location>(location,HttpStatus.OK);
		
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Location> findLocation(@RequestParam("id") int id){
		Location loc = this.adverService.findLocation(id);
		return new ResponseEntity<Location>(loc,HttpStatus.OK);
	}
}
