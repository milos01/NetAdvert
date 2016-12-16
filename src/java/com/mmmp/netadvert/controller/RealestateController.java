package com.mmmp.netadvert.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mmmp.netadvert.model.RealestateCategory;
import com.mmmp.netadvert.model.RealestateType;
import com.mmmp.netadvert.model.TechnicalEquipment;
import com.mmmp.netadvert.service.AdverService;

@RestController
@RequestMapping(value="api/realestate")
public class RealestateController {
	
	@Autowired
	private AdverService adverService;

	@Autowired(required = true)
	public void setAdverService(AdverService adverService) {
		this.adverService = adverService;
	}
	
	@RequestMapping(value="/type", method=RequestMethod.GET)
	public ResponseEntity<List<RealestateType>> findTypes(){
		List<RealestateType> list = this.adverService.findAllRealstateTypes();
		return new ResponseEntity<List<RealestateType>>(list,HttpStatus.OK);
	}
	
	@RequestMapping(value="/type/{id}", method=RequestMethod.GET)
	public ResponseEntity<RealestateType> findType(@PathVariable("id") int id){
		RealestateType rt = this.adverService.findRealestateTypeById(id);
		return new ResponseEntity<RealestateType>(rt,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/category", method=RequestMethod.GET)
	public ResponseEntity<List<RealestateCategory>> findCategories(){
		List<RealestateCategory> list = this.adverService.findAllRealestateCategory();
		return new ResponseEntity<List<RealestateCategory>>(list,HttpStatus.OK);
	}
	
	@RequestMapping(value="/category/{id}", method=RequestMethod.GET)
	public ResponseEntity<RealestateCategory> findCategory(@PathVariable("id") int id){
		RealestateCategory rc = this.adverService.findRealestateCategory(id);
		return new ResponseEntity<RealestateCategory>(rc,HttpStatus.OK);
	}
	
	@RequestMapping(value="/equipment", method=RequestMethod.GET)
	public ResponseEntity<List<TechnicalEquipment>> findEquipments(){
		List<TechnicalEquipment> list = this.adverService.allEquipment();
		return new ResponseEntity<List<TechnicalEquipment>>(list,HttpStatus.OK);
	}
	
	@RequestMapping(value="/equipment/{id}", method=RequestMethod.GET)
	public ResponseEntity<TechnicalEquipment> findEquipment(@PathVariable("id") int id){
		TechnicalEquipment t = this.adverService.findTechnicalEquipmentById(id);
		return new ResponseEntity<TechnicalEquipment>(t,HttpStatus.OK);
	}
	
	/*
	
	public ResponseEntity<RealestateType> findTypeByName(@RequestParam("type") String name){
		RealestateType rt = this.adverService.findRealestateType(name);
		return new ResponseEntity<RealestateType>(rt,HttpStatus.OK);
	}
	
	public ResponseEntity<RealestateCategory> findCategoryByName(@RequestParam("category") String name){
		RealestateCategory rc = this.adverService.findRealestateCategory(name);
		return new ResponseEntity<RealestateCategory>(rc,HttpStatus.OK);
	}
	
	*/

}
