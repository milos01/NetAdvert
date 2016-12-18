package com.mmmp.netadvert.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmmp.netadvert.model.Advert;
import com.mmmp.netadvert.model.AdvertRating;
import com.mmmp.netadvert.model.User;
import com.mmmp.netadvert.service.AdverService;

@RestController
@RequestMapping(value = "/api/advert")
public class AdvertRatingController {
	
	@Autowired
	private AdverService adverService;

	@Autowired(required = true)
	public void setAdverService(AdverService adverService) {
		this.adverService = adverService;
	}
	
	@RequestMapping(value="/{id}/rating", method = RequestMethod.POST)
	public ResponseEntity<Advert> addAdvertRating(@PathVariable("id") int aid, HttpSession session,@RequestParam("rating") int addedRating){
		User u = (User) session.getAttribute("logedUser");
		if(u==null){
	        return new ResponseEntity<> (HttpStatus.FORBIDDEN);
	    }
		Advert a = this.adverService.findAdvert(aid);
		if(a==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(a.getIs_deleted()==true){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(addedRating<=0){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Set<AdvertRating> ratings = u.getAdvertRatings();
		for(AdvertRating ar : ratings){
			if(ar.getAdvert().getId()==a.getId()){
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		
		Set<AdvertRating> allAdvertRatings = a.getAdvertRatings();
		int count = allAdvertRatings.size()+1;
		int sum = 0;
		for(AdvertRating r : allAdvertRatings){
			sum+=r.getRating();
		}
		sum+=addedRating;
		double updatedRating = sum/count;
		a.setAdvert_rate(updatedRating);
		
		AdvertRating advertRating = new AdvertRating();
		advertRating.setAdvert(a);
		advertRating.setUser(u);
		advertRating.setRating(addedRating);
		this.adverService.addAdvertRating(advertRating);
		
		a.getAdvertRatings().add(advertRating);
		this.adverService.updateAdvert(a);
		u.getAdvertRatings().add(advertRating);
		this.adverService.updateUser(u);
		
		return new ResponseEntity<Advert>(a, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}/rating", method = RequestMethod.GET)
	public ResponseEntity<List<AdvertRating>> getAdvertRatings(@PathVariable("id") int aid, HttpSession session){
		User u = (User) session.getAttribute("logedUser");
		if(u==null){
	        return new ResponseEntity<> (HttpStatus.FORBIDDEN);
	    }
		Advert a = this.adverService.findAdvert(aid);
		if(a==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(a.getIs_deleted()==true){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Set<AdvertRating> allAdvertRatings = a.getAdvertRatings();
		List<AdvertRating> ret = new ArrayList<AdvertRating>();
		ret.addAll(allAdvertRatings);
		return new ResponseEntity<List<AdvertRating>>(ret, HttpStatus.OK);
	}
}
