package com.mmmp.netadvert.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.mmmp.netadvert.model.*;
import com.mmmp.netadvert.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmmp.netadvert.DTO.AdvertDTO;
import com.mmmp.netadvert.DTO.LocationDTO;
import com.mmmp.netadvert.DTO.RealestateDTO;
import com.mmmp.netadvert.DTO.SearchDTO;
import com.mmmp.netadvert.service.AdverService;

@RestController
@RequestMapping(value = "api/advert")
public class AdvertController {

	@Autowired
	private AdverService adverService;

	@Autowired
	private AdvertService advertService;

	@Autowired(required = true)
	public void setAdverService(AdverService adverService) {
		this.adverService = adverService;
	}
	/**
	 * This method is part of advert rest service. Method will add new advert with params sent from form. 
	 * User will be send in session, and in this method it will be checked for possible errors like send params are wrong,
	 * like area and cost are negative numbers,
	 * or if equipment list size of send category doesnt match size of sent equipment list,
	 * and it checkes if location already exists so it wont be made again in database 
	 * @param advertDTO object holding all information about sent advert
	 * @param session HttpSession object for setting user on session
	 * @return Http response 200 OK
	 * @see Advert
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Advert> createAdvert(@RequestBody AdvertDTO advertDTO, HttpSession session) {

		User u = (User) session.getAttribute("logedUser");
		u = this.adverService.findUser("milossm94@hotmail.com");
		if(u==null){
	        return new ResponseEntity<> (HttpStatus.FORBIDDEN);
	    }
		
		RealestateCategory rc = this.adverService.findRealestateCategory(advertDTO.getRealestate().getCategory().getRealestateCategoryId());
		if (rc == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		boolean check = false;
		RealestateType type = null;
		for (RealestateType t : rc.getTypes()) {
			if (t.getId() == advertDTO.getRealestate().getType().getRealestateTypeId()) {
				check = true;
				type = t;
				break;
			}
		}
		if (!check) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		List<Boolean> equipments = advertDTO.getRealestate().getEquipments();
		if(equipments==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(rc.getEquipments().size()!=equipments.size()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(advertDTO.getCost()<=0 || advertDTO.getRealestate().getArea()<=0){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(advertDTO.getRealestate().getLocation().getCity()==null || advertDTO.getRealestate().getLocation().getRegion()==null || advertDTO.getRealestate().getLocation().getStreet()==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		String locCity = advertDTO.getRealestate().getLocation().getCity().trim();
		String locRegion = advertDTO.getRealestate().getLocation().getRegion().trim();
		String locStreet = advertDTO.getRealestate().getLocation().getStreet().trim();
		int locPostalCode = advertDTO.getRealestate().getLocation().getPostalCode();
		int locStreetNumber = advertDTO.getRealestate().getLocation().getStreetNumber();
		Location location = this.adverService.checkForExistingLocation(locStreet, locStreetNumber, locRegion,
				locCity, locPostalCode);
		if (location == null) {
			location = new Location();
			location.setCity(locCity);
			location.setRegion(locRegion);
			location.setStreet(locStreet);
			location.setPostalCode(locPostalCode);
			location.setStreetNumber(locStreetNumber);
			this.adverService.createLocation(location);
		}
		List<TechnicalEquipment> equipmentList = new ArrayList<TechnicalEquipment>();
		equipmentList.addAll(rc.getEquipments());
		Collections.sort(equipmentList, new Comparator<TechnicalEquipment>() {
			public int compare(TechnicalEquipment m1, TechnicalEquipment m2) {
				if (m1.getId() > m2.getId()) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		Realestate r = this.adverService.findRealestate(advertDTO.getRealestate().getRealestateId());
		boolean exists = false;
		if(r!=null){
			exists = true;
		}
		else{
			r = new Realestate();
		}
		r.setRealestateName(advertDTO.getRealestate().getRealestateName());
		r.setType(type);
		r.setArea(advertDTO.getRealestate().getArea());
		r.setHeating(advertDTO.getRealestate().isHeating());
		r.setLocation(location);
		r.setCategory(rc);
		r.setTechnicalEquipments(new HashSet<TechnicalEquipment>());
		if(exists == true){
			this.adverService.updateRealestate(r);
		}
		else{
			this.adverService.addRealestate(r);
		}
		Realestate rs = this.adverService.findRealestate(r.getId());
		
		for (int i = 0; i < equipmentList.size(); i++) {
			if (equipments.get(i) == true) {
				rs.getTechnicalEquipments().add(equipmentList.get(i));
			}
		}
		this.adverService.updateRealestate(rs);
		Advert advert = new Advert();
		advert.setUser(u);
		advert.setRealestate(rs);
		Date d = new Date(new java.util.Date().getYear(), new java.util.Date().getMonth(), new java.util.Date().getDate());
		advert.setCreated_at(d);
		advert.setUpdated_at(d);
		Date expire = new Date(new java.util.Date().getYear(), new java.util.Date().getMonth() + 1, new java.util.Date().getDate());
		advert.setExpire_date(expire);
		advert.setIs_sold(false);
		advert.setIs_deleted(false);
		advert.setAdvert_rate(0);
		advert.setContact(advertDTO.getContact());
		advert.setRent_sale(advertDTO.getRent_sale());
		advert.setDescription(advertDTO.getDescription());
		advert.setCost(advertDTO.getCost());
		this.adverService.addAdvert(advert);
		if(u.getAdverts()==null){
			u.setAdverts(new HashSet<Advert>());
		}
		u.getAdverts().add(advert);
		this.adverService.updateUser(u);
		
		return new ResponseEntity<Advert> (advert,HttpStatus.OK);
	}

	/**
	 * This method is part of advert rest service. Method will update existing advert with params sent from form. 
	 * User will be send in session, and in this method it will be checked for possible errors like send params are wrong,
	 * like area and cost are negative numbers,
	 * or if equipment list size of send category doesnt match size of sent equipment list,
	 * and it checkes if location already exists so it wont be made again in database 
	 * and if advert doesnt exist or is deleted before
	 * @param advertDTO object containing all information about updated advert
	 * @param session HttpSession object for setting user on session
	 * @return Http response 200 OK
	 * @see Advert
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Advert> updateAdvert(@RequestBody AdvertDTO advertDTO, HttpSession session) {
		User u = (User) session.getAttribute("logedUser");
		u = this.adverService.findUser("milossm94@hotmail.com");
		Advert advert = this.adverService.findAdvert(advertDTO.getAdvertId());
		
		if(u!=null){
			Set<Advert> userAdverts = u.getAdverts();
			if(userAdverts== null){
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			else{
				boolean exists=false;
				for(Advert a : userAdverts){
					if(a.getId()==advert.getId()){
						exists = true;
						break;
					}
				}
				if(exists==false){
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
		}
		else{
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		if(advert.getIs_deleted()==true){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		advert.setContact(advertDTO.getContact().trim());
		advert.setDescription(advertDTO.getDescription());
		advert.setRent_sale(advertDTO.getRent_sale());
		advert.setCost(advertDTO.getCost());
		Realestate r = this.adverService.findRealestate(advert.getRealestate().getId());
		if(r==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(r.getId()!=advertDTO.getRealestate().getRealestateId()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		r.setRealestateName(advertDTO.getRealestate().getRealestateName().trim());
		r.setArea(advertDTO.getRealestate().getArea());
		r.setHeating(advertDTO.getRealestate().isHeating());
		
		RealestateCategory rc = this.adverService.findRealestateCategory(advertDTO.getRealestate().getCategory().getRealestateCategoryId());
		if(rc==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Set<RealestateType> types = rc.getTypes();
		boolean exists = false;
		for(RealestateType t: types){
			if(t.getId()==advertDTO.getRealestate().getType().getRealestateTypeId()){
				exists = true;
				r.setCategory(rc);
				r.setType(t);
				break;
			}
		}
		if(exists==false){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(rc.getEquipments().size()!=advertDTO.getRealestate().getEquipments().size()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		String locStreet = advertDTO.getRealestate().getLocation().getStreet().trim();
		int locStreetNumber = advertDTO.getRealestate().getLocation().getStreetNumber();
		String locRegion = advertDTO.getRealestate().getLocation().getRegion().trim();
		String locCity = advertDTO.getRealestate().getLocation().getCity().trim();
		int locPostalCode = advertDTO.getRealestate().getLocation().getPostalCode();
		Location l = this.adverService.checkForExistingLocation(locStreet, locStreetNumber, locRegion, locCity, locPostalCode);
		if(l==null){
			l = new Location();
			l.setCity(locCity);
			l.setPostalCode(locPostalCode);
			l.setRegion(locRegion);
			l.setStreet(locStreet);
			l.setStreetNumber(locStreetNumber);
			this.adverService.createLocation(l);
		}
		r.setLocation(l);
		
		
		List<TechnicalEquipment> equipmentList = new ArrayList<TechnicalEquipment>();
		equipmentList.addAll(rc.getEquipments());
		Collections.sort(equipmentList, new Comparator<TechnicalEquipment>() {
			public int compare(TechnicalEquipment m1, TechnicalEquipment m2) {
				if (m1.getId() > m2.getId()) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		r.getTechnicalEquipments().clear();
		for (int i = 0; i < equipmentList.size(); i++) {
			if (advertDTO.getRealestate().getEquipments().get(i) == true) {
				r.getTechnicalEquipments().add(equipmentList.get(i));
			}
		}
		this.adverService.updateRealestate(r);
		Date d = new Date(new java.util.Date().getYear(), new java.util.Date().getMonth(), new java.util.Date().getDate());
		advert.setUpdated_at(d);
		
		this.adverService.updateAdvert(advert);
		
		return new ResponseEntity<Advert> (advert, HttpStatus.OK);
	}
	
	/**
	 * This method is part of advert rest service. Advert which id is send in the request will be deleted
	 *  if advert is not previously deleted and if user who sends request id advert owner.
	 * @param id advert id which is being deleted
	 * @param session
	 * @return Http response 200 OK
	 * @see Advert
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAdvert(@PathVariable("id") int id, HttpSession session){
		User u = (User) session.getAttribute("logedUser");
		u = this.adverService.findUser("milossm94@hotmail.com");
		u = this.adverService.findUser(u.getEmail());
		if(u!=null){
			Set<Advert> userAdverts = u.getAdverts();
			if(userAdverts==null){
				return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
			}
			else{
				boolean exists=false;
				for(Advert a : userAdverts){
					if(a.getId()==id){
						this.adverService.deleteAdvert(a);
						exists = true;
						return new ResponseEntity<Void>(HttpStatus.OK);
					}
				}
				if(exists==false){
					return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
	}
	
	/**
	 * This method is part of advert rest service. Advert id which is being returned is send in request
	 * and user will get requested advert if it's not deleted.
	 * @param id advert id which is being returned
	 * @return Http response 200 OK
	 * @see Advert
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Advert> getAdvertById(@PathVariable("id") int id){
		Advert a = this.adverService.findAdvert(id);
		if(a==null || a.getIs_deleted()==true){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Advert>(a, HttpStatus.OK);
	}
	
	/**

	 * This method is part of advert rest service. User will get in response all adverts which are not deleted. 
	 * @return Http status 200 OK
	 * @see Advert
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<Advert>> getAllAdverts(Pageable page){
		Page<Advert> advertList = this.advertService.findAll(page);
		return new ResponseEntity<Page<Advert>>(advertList, HttpStatus.OK);
	}

	/**
	 * This method is part of advert rest service. Method will return profile picture of advert
	 * which id is being send in request if advert in not deleted.
	 * @return Http status 200 OK
	 * @see Advert
	 */
	@RequestMapping(value="/{id}/mainPicture", method=RequestMethod.GET)
	public ResponseEntity<Picture> getAdvertMainPicture(@PathVariable("id") int id){
		Advert a = this.adverService.findAdvert(id);
		if(a==null || a.getIs_deleted()==true){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Picture pic = this.adverService.getAdvertMainPicture(id);
		return new ResponseEntity<Picture>(pic, HttpStatus.OK);
	}
	
	/**
	 * This method is part of advert rest service. It allows user to buy realestate
	 * which is being advertised, if advert is not deleted or previously sold.
	 * @param aid advert id which is being bought
	 * @param session
	 * @return Http status 200 OK
	 * @see Advert
	 */
	@RequestMapping(value="/{id}/buy", method = RequestMethod.PUT)
	public ResponseEntity<Advert> buyAdvert(@PathVariable("id") int aid, HttpSession session){
		User u = (User) session.getAttribute("logedUser");
		u = this.adverService.findUser("milossm94@hotmail.com");
		if(u!=null){
			Advert a = this.adverService.findAdvert(aid);
			if (a==null){
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			if(a.getIs_deleted()==true){
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			if(a.getUser().getId()==u.getId()){
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			if(a.getIs_sold()==true){
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			a.setIs_sold(true);
			this.adverService.updateAdvert(a);
			return new ResponseEntity<Advert>(a, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
	
	/**
	 * This method is part of advert rest service.
	 * @param session
	 * @param search
	 * @return
	 */
	@RequestMapping(value="/search", method = RequestMethod.POST)
	public ResponseEntity<List<Advert>> searchAdvert(HttpSession session, @RequestBody SearchDTO search){
		if(search.getRent_sale()==null || search.getHeating()==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(search.getPrice_from()>search.getPrice_to()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(search.getArea_from()>search.getArea_to()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(search.getPrice_from()<0 || search.getPrice_to()<0 || search.getArea_from()<0 || search.getArea_to()<0){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		List<TechnicalEquipment> te = this.adverService.allEquipment();
		Collections.sort(te, new Comparator<TechnicalEquipment>() {
			public int compare(TechnicalEquipment m1, TechnicalEquipment m2) {
				if (m1.getId() > m2.getId()) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		List<TechnicalEquipment> tech = new ArrayList<TechnicalEquipment>();
		for(TechnicalEquipment tt : te){
			for(String s : search.getEquipments()){
				if(tt.getEquipmentName().equals(s.trim())){
					tech.add(tt);
					break;
				}
			}
		}
		return null;
	}

}
