package com.mmmp.netadvert.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmmp.netadvert.DTO.SearchDTO;
import com.mmmp.netadvert.model.Advert;
import com.mmmp.netadvert.model.Location;
import com.mmmp.netadvert.model.Realestate;
import com.mmmp.netadvert.model.RealestateCategory;
import com.mmmp.netadvert.model.RealestateType;
import com.mmmp.netadvert.model.TechnicalEquipment;
import com.mmmp.netadvert.model.User;
import com.mmmp.netadvert.service.AdverService;

@RestController
@RequestMapping(value = "api/advert")
public class AdvertController {

	@Autowired
	private AdverService adverService;

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
	 * @param contact  advert contant sent in form
	 * @param description advert description sent in form
	 * @param rent_sale true if advert is for sale, false if advert is for rent
	 * @param real_name new realestate name sent in form
	 * @param real_type_id type of new realestate sent in form 
	 * @param real_cost cost of realestate that is being advertised in form
	 * @param real_area realestate area sent in form
	 * @param real_category_id realestate category sent in form
	 * @param real_heating true if realestate has heating otherwise false
	 * @param loc_street realestate street location sent in form
	 * @param loc_street_number realestate street number sent in form
	 * @param loc_region realestate region location sent in form
	 * @param loc_city realestate city location sent in form
	 * @param loc_postal_code realestate postal code sent in form
	 * @param equipments realestate equipments sent in form
	 * @param session HttpSession object for setting user on session
	 * @return Http response 200 OK
	 * @see Advert
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Advert> createAdvert(@RequestParam("contact") String contact,@RequestParam("description") String description,@RequestParam("rent_sale") boolean rent_sale, @RequestParam("real_name") String real_name,
			@RequestParam("real_type_id") int real_type_id, @RequestParam("real_cost") double real_cost,
			@RequestParam("real_area") double real_area, @RequestParam("real_category_id") int real_category_id,
			@RequestParam("real_heating") boolean real_heating, @RequestParam("loc_street") String loc_street,
			@RequestParam("loc_street_number") int loc_street_number, @RequestParam("loc_region") String loc_region,
			@RequestParam("loc_city") String loc_city, @RequestParam("loc_postal_code") int loc_postal_code,
			@RequestParam("equipments") List<Boolean> equipments, HttpSession session) {

		User u = (User) session.getAttribute("logedUser");
		if(u==null){
	        return new ResponseEntity<> (HttpStatus.FORBIDDEN);
	    }
		
		RealestateCategory rc = this.adverService.findRealestateCategory(real_category_id);
		if (rc == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		boolean check = false;
		RealestateType type = null;
		for (RealestateType t : rc.getTypes()) {
			if (t.getId() == real_type_id) {
				check = true;
				type = t;
				break;
			}
		}
		if (!check) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(rc.getEquipments().size()!=equipments.size()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(real_cost<=0 || real_area<=0){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Location location = this.adverService.checkForExistingLocation(loc_street, loc_street_number, loc_region,
				loc_city, loc_postal_code);
		if (location == null) {
			location = new Location();
			location.setCity(loc_city.trim());
			location.setRegion(loc_region.trim());
			location.setStreet(loc_street.trim());
			location.setPostalCode(loc_postal_code);
			location.setStreetNumber(loc_street_number);
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
		Realestate r = new Realestate();
		r.setRealestateName(real_name);
		r.setType(type);
		r.setCost(real_cost);
		r.setArea(real_area);
		r.setHeating(real_heating);
		r.setLocation(location);
		r.setCategory(rc);
		r.setTechnicalEquipments(new HashSet<TechnicalEquipment>());
		this.adverService.addRealestate(r);
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
		advert.setContact(contact);
		advert.setRent_sale(rent_sale);
		advert.setDescription(description);
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
	 * @param advert_id advert id information send in form that already exists
	 * @param contact advert contant sent in form
	 * @param description advert description
	 * @param rent_sale true if advert is for sale, false if advert is for rent
	 * @param real_name new realestate name sent in form
	 * @param real_type_id type of new realestate sent in form 
	 * @param real_cost cost of realestate that is being advertised in form
	 * @param real_area realestate area sent in form
	 * @param real_category_id realestate category sent in form
	 * @param real_heating true if realestate has heating otherwise false
	 * @param loc_street realestate street location sent in form
	 * @param loc_street_number realestate street number sent in form
	 * @param loc_region realestate region location sent in form
	 * @param loc_city realestate city location sent in form
	 * @param loc_postal_code realestate postal code sent in form
	 * @param equipments realestate equipments sent in form
	 * @param session HttpSession object for setting user on session
	 * @return Http response 200 OK
	 * @see Advert
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Advert> updateAdvert(@RequestParam("advert_id") int advert_id,@RequestParam("contact") String contact,@RequestParam("description") String description,@RequestParam("rent_sale") boolean rent_sale, @RequestParam("real_name") String real_name,
			@RequestParam("real_type_id") int real_type_id, @RequestParam("real_cost") double real_cost,
			@RequestParam("real_area") double real_area, @RequestParam("real_category_id") int real_category_id,
			@RequestParam("real_heating") boolean real_heating, @RequestParam("loc_street") String loc_street,
			@RequestParam("loc_street_number") int loc_street_number, @RequestParam("loc_region") String loc_region,
			@RequestParam("loc_city") String loc_city, @RequestParam("loc_postal_code") int loc_postal_code,
			@RequestParam("equipments") List<Boolean> equipments, HttpSession session) {
		User u = (User) session.getAttribute("logedUser");
		u=this.adverService.findUser(u.getEmail());
		Advert advert = this.adverService.findAdvert(advert_id);
		
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
		Advert a = this.adverService.findAdvert(advert.getId());
		if(a.getIs_deleted()==true){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		a.setContact(advert.getContact().trim());
		a.setDescription(advert.getDescription());
		a.setRent_sale(advert.getRent_sale());
		Realestate r = a.getRealestate();
		r.setRealestateName(real_name.trim());
		r.setCost(real_cost);
		r.setArea(real_area);
		r.setHeating(real_heating);
		
		RealestateCategory rc = this.adverService.findRealestateCategory(real_category_id);
		if(rc==null){
			System.out.println(2);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Set<RealestateType> types = rc.getTypes();
		boolean exists = false;
		for(RealestateType t: types){
			if(t.getId()==real_type_id){
				exists = true;
				r.setCategory(rc);
				r.setType(t);
				break;
			}
		}
		if(exists==false){
			System.out.println(3);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(rc.getEquipments().size()!=equipments.size()){
			System.out.println(4);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Location l = this.adverService.checkForExistingLocation(loc_street, loc_street_number, loc_region, loc_city, loc_postal_code);
		if(l==null){
			l = new Location();
			l.setCity(loc_city.trim());
			l.setPostalCode(loc_postal_code);
			l.setRegion(loc_region.trim());
			l.setStreet(loc_street.trim());
			l.setStreetNumber(loc_street_number);
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
			if (equipments.get(i) == true) {
				r.getTechnicalEquipments().add(equipmentList.get(i));
			}
		}
		
		this.adverService.updateRealestate(r);
		Date d = new Date(new java.util.Date().getYear(), new java.util.Date().getMonth(), new java.util.Date().getDate());
		a.setUpdated_at(d);
		
		this.adverService.updateAdvert(a);
		
		return new ResponseEntity<Advert> (a, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAdvert(@PathVariable("id") int id, HttpSession session){
		User u = (User) session.getAttribute("logedUser");
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
	 * 
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Advert> getAdvertById(@PathVariable("id") int id, HttpSession session){
		Advert a = this.adverService.findAdvert(id);
		if(a==null || a.getIs_deleted()==true){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Advert>(a, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Advert>> getAllAdverts(HttpSession session){
		List<Advert> advertList = this.adverService.allAdverts();
		for(int i = advertList.size()-1; i>=0; i--){
			if(advertList.get(i).getIs_deleted()==true){
				advertList.remove(i);
			}
		}
		return new ResponseEntity<List<Advert>>(advertList, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param aid
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/{id}/buy", method = RequestMethod.PUT)
	public ResponseEntity<Advert> buyAdvert(@PathVariable("id") int aid, HttpSession session){
		User u = (User) session.getAttribute("logedUser");
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
	 * 
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
