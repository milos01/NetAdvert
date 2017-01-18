package com.mmmp.netadvert.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmmp.netadvert.DTO.AdvertNewRate;
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
	
	/**
	 * This method is part of advert rest service. This method saves user rating of an advert
	 * which is not deleted and it calculates new average advert rate.
	 * @param aid advert id which is being rated
	 * @param session
	 * @param addedRating user advert rating
	 * @return Http status 200 OK
	 * @see Advert
	 */
	@RequestMapping(value="/{id}/rating", method = RequestMethod.POST)
	public ResponseEntity<Advert> addAdvertRating(@PathVariable("id") int aid, HttpSession session,@RequestBody AdvertNewRate addedRating,Principal user){
		User u = this.adverService.findUser(user.getName());
		System.out.println("Aaaaaaaaaaaaa");
		if(u==null){
	        return new ResponseEntity<> (HttpStatus.FORBIDDEN);
	    }
		Advert a = this.adverService.findAdvert(aid);
		if(a==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(a.getDeleted()==true){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(addedRating.getRate()<=0){
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
		sum+=addedRating.getRate();
		double updatedRating = sum/count;
		a.setAdvert_rate(updatedRating);
		
		AdvertRating advertRating = new AdvertRating();
		advertRating.setAdvert(a);
		advertRating.setUser(u);
		advertRating.setRating(addedRating.getRate());
		this.adverService.addAdvertRating(advertRating);
		
		a.getAdvertRatings().add(advertRating);
		this.adverService.updateAdvert(a);
		u.getAdvertRatings().add(advertRating);
		this.adverService.updateUser(u);
		
		return new ResponseEntity<Advert>(a, HttpStatus.OK);
	}
	
	/**
	 * This method is part of advert rest service. Method returns all ratings of an advert which is not deleted.
	 * @param aid advert id which ratings are being returned
	 * @param session
	 * @return Http status 200 OK
	 * @see AdvertRating
	 */
	@RequestMapping(value="/{id}/rating", method = RequestMethod.GET)
	public ResponseEntity<List<AdvertRating>> getAdvertRatings(@PathVariable("id") int aid, HttpSession session){
		User u = (User) session.getAttribute("logedUser");
		u = this.adverService.findUser("milossm94@hotmail.com");
		if(u==null){
	        return new ResponseEntity<> (HttpStatus.FORBIDDEN);
	    }
		Advert a = this.adverService.findAdvert(aid);
		if(a==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(a.getDeleted()==true){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Set<AdvertRating> allAdvertRatings = a.getAdvertRatings();
		List<AdvertRating> ret = new ArrayList<AdvertRating>();
		ret.addAll(allAdvertRatings);
		return new ResponseEntity<List<AdvertRating>>(ret, HttpStatus.OK);
	}
	
	/**
	 * This method is part of advert rest service.Method will update user advert rating.
	 * @param aid advert id which rating is being updated
	 * @param session
	 * @param addedRating user rating of an advert
	 * @return Http status 200 OK
	 * @see Advert
	 */
	@RequestMapping(value="/{id}/rating", method = RequestMethod.PUT)
	public ResponseEntity<Advert> updateAdvertRating(@PathVariable("id") int aid,Principal user, HttpSession session, @RequestParam("rating") int addedRating){
		User u = this.adverService.findUser(user.getName());
		if(u==null){
	        return new ResponseEntity<> (HttpStatus.FORBIDDEN);
	    }
		Advert a = this.adverService.findAdvert(aid);
		if(a==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(a.getDeleted()==true){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(addedRating<=0){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Set<AdvertRating> ratings = u.getAdvertRatings();
		boolean exists = false;
		AdvertRating updatedR = new AdvertRating();
		for(AdvertRating ar : ratings){
			if(ar.getAdvert().getId()==a.getId()){
				exists = true;
				updatedR = ar;
				break;
			}
		}
		if(exists==false){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		updatedR.setRating(addedRating);
		this.adverService.updateAdvertRating(updatedR);
		
		this.adverService.updateAdvert(a);
		a=this.adverService.findAdvert(aid);
		Set<AdvertRating> allAdvertRatings = a.getAdvertRatings();
		int count = allAdvertRatings.size();
		int sum = 0;
		for(AdvertRating r : allAdvertRatings){
			sum+=r.getRating();
		}
		double updatedRating = sum/count;
		a.setAdvert_rate(updatedRating);
		
		
		this.adverService.updateAdvert(a);
		
		return new ResponseEntity<Advert>(a, HttpStatus.OK);
	}
	
	/**
	 * This method is part of advert rest service. Method will delete user advert rating.
	 * @param aid advert id from which user advert rating is being deleted.
	 * @param session
	 * @return Http status 200 OK
	 * @see Advert
	 */
	@RequestMapping(value="/{id}/rating", method = RequestMethod.DELETE)
	public ResponseEntity<Advert> deleteAdvertRating(@PathVariable("id") int aid,Principal user, HttpSession session){
		User u = this.adverService.findUser(user.getName());
		if(u==null){
	        return new ResponseEntity<> (HttpStatus.FORBIDDEN);
	    }
		Advert a = this.adverService.findAdvert(aid);
		if(a==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(a.getDeleted()==true){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Set<AdvertRating> ratings = u.getAdvertRatings();
		boolean exists = false;
		AdvertRating deleteR = new AdvertRating();
		for(AdvertRating ar : ratings){
			if(ar.getAdvert().getId()==a.getId()){
				exists = true;
				deleteR = ar;
				break;
			}
		}
		if(exists==false){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		this.adverService.deleteAdvertRating(deleteR);
		
		a=this.adverService.findAdvert(aid);
		Set<AdvertRating> allAdvertRatings = a.getAdvertRatings();
		int count = allAdvertRatings.size();
		if(count==0){
			a.setAdvert_rate(0);
		}
		else{
			int sum = 0;
			for(AdvertRating r : allAdvertRatings){
				sum+=r.getRating();
			}
			double updatedRating = sum/count;
			a.setAdvert_rate(updatedRating);
		}
		this.adverService.updateAdvert(a);
		
		return new ResponseEntity<Advert>(a, HttpStatus.OK);
	}
	
	@RequestMapping(value="/findUserAdvertRait", method = RequestMethod.GET)
	public ResponseEntity<AdvertRating> getUserAndAdRait(@RequestParam("user_id") int idU,@RequestParam("advert_id") int idA){
		
		AdvertRating ar = this.adverService.getUserOfAdvertRaiting(idU, idA);
		if (ar==null){
			return new ResponseEntity<AdvertRating>(HttpStatus.OK);
		}
		
		return new ResponseEntity<AdvertRating>(ar,HttpStatus.OK);
	}
	
}
