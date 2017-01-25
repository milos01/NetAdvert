package com.mmmp.netadvert.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmmp.netadvert.DTO.SearchDTO;
import com.mmmp.netadvert.model.Advert;
import com.mmmp.netadvert.model.TechnicalEquipment;
import com.mmmp.netadvert.service.AdverService;

@RestController
@RequestMapping(value = "api")
public class SearchController {
	
	@Autowired
	private AdverService adverService;

	@Autowired(required = true)
	public void setAdverService(AdverService adverService) {
		this.adverService = adverService;
	}
	
	/**
	 * This method is part of advert rest service.
	 * @return
	 */
	@RequestMapping(value="/search", method = RequestMethod.GET)
	public ResponseEntity<Page<Advert>> searchAdvert(@RequestParam Map<String, String> params, Pageable pageable){
		Map<String, Object> map = new HashMap<String, Object>();
		
		putDouble(map, "priceFrom", params);
		putDouble(map, "priceTo", params);
		putDouble(map, "areaFrom", params);
		putDouble(map, "areaTo", params);
		
		putBoolean(map, "rent", params);
		putBoolean(map, "sale", params);
		
		String s = params.remove("heating");
		if(s !=null){
			Boolean b = Boolean.valueOf(s);
			map.put("heating", b);
		}
		putInteger(map, "streetNumber", params);
		putInteger(map, "postalCode", params);
		putInteger(map, "size", params);
		map.putAll(params);
		List<TechnicalEquipment> te = this.adverService.allEquipment();
		List<TechnicalEquipment> tech = new ArrayList<TechnicalEquipment>();
		for(TechnicalEquipment tt : te){
			s = (String)map.remove(tt.getEquipmentName());
			if(s!=null){
				Boolean bb = Boolean.valueOf(s);
				if(bb!=null && bb==true){
					tech.add(tt);
				}
			}
		}
		System.out.println(" *************************** ");
		try{
			Page<Advert> retList = this.adverService.searchAdverts(map, tech, pageable);
			return new ResponseEntity<Page<Advert>>(retList, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<Page<Advert>>(HttpStatus.NOT_FOUND);
		}
		
		
		
		
	}
	
	public static Integer tryParseInteger(String text) {
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Double tryParseDouble(String text) {
		try {
			return Double.parseDouble(text);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static void putDouble(Map<String, Object> map, String name, Map<String, String> params){
		String s = params.remove(name);
		if(s!=null){
			Double d = tryParseDouble(s);
			map.put(name, d);
		}
	}
	
	public static void putInteger(Map<String, Object> map, String name, Map<String, String> params){
		String s = params.remove(name);
		if(s !=null){
			Integer i = tryParseInteger(s);
			map.put(name, i);
		}
	}
	
	public static void putBoolean(Map<String, Object> map, String name, Map<String, String> params){
		String s = params.remove(name);
		if(s !=null){
			Boolean b = Boolean.valueOf(s);
			map.put(name, b);
		}
		else{
			map.put(name, true);
		}
	}
	
}
