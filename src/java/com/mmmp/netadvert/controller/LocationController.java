package com.mmmp.netadvert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmmp.netadvert.DTO.LocationDTO;
import com.mmmp.netadvert.model.Location;
import com.mmmp.netadvert.service.AdverService;

@RestController
@RequestMapping(value="/api/location")
public class LocationController {

	
	@Autowired
	private AdverService adverService;
	
	@Autowired(required = true)
	public void setAdverService(AdverService ps) {
		this.adverService = ps;
	}
	/**
	 * Method create new location.
	 * @param location - the mapped object from the form 
	 * @return location object, http response 200 ok
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Location> newLocation(@RequestBody LocationDTO location) {
		String street = location.getStreet();
		String city = location.getCity();
		String region = location.getRegion();
		int postal = location.getPostalCode();
		int streetNumber = location.getStreetNumber();
		if (street == null || street.equals("") || city == null
				|| city.equals("") || streetNumber+"" == null || (streetNumber+"").equals("")) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Location loc = new Location();
		loc.setCity(city.split(",")[0]);
		loc.setPostalCode(postal);
		loc.setRegion(region);
		loc.setStreet(street.split(",")[0]);
		loc.setStreetNumber(streetNumber);
		this.adverService.createLocation(loc);
		return new ResponseEntity<Location>(loc,HttpStatus.OK);
		
	}
	/**
	 * Method update old location.
	 * @param location - the mapped object from the form 
	 * @return location object, http response 200 ok
	 */
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Location> updateLocation(@RequestBody LocationDTO location){
		String street = location.getStreet();
		String city = location.getCity();
		String region = location.getRegion();
		int postal = location.getPostalCode();
		int streetNumber = location.getStreetNumber();
		int idL = location.getLocationId();
		if (street == null || street.equals("") || city == null
				|| city.equals("") || streetNumber+"" == null || (streetNumber+"").equals("")) {
			return new ResponseEntity<Location>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		Location loc = new Location();
		loc.setId(idL);
		loc.setCity(city);
		loc.setPostalCode(postal);
		loc.setRegion(region);
		loc.setStreet(street);
		loc.setStreetNumber(streetNumber);
		this.adverService.updateLocation(loc);
		return new ResponseEntity<Location>(loc,HttpStatus.OK);
		
	}
	
	/**
	 * Method will return one location object on the basis of id.
	 * @param id - id of location object
	 * @return location object, http response 200 ok
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Location> findLocation(@RequestParam("id") int id){
		Location loc = this.adverService.findLocation(id);
		if(loc!=null){
			return new ResponseEntity<Location>(loc,HttpStatus.OK);
		}
		else{
			return new ResponseEntity<Location>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
