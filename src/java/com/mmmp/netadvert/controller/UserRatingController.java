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
import com.mmmp.netadvert.model.UserRating;
import com.mmmp.netadvert.service.AdverService;

@RestController
@RequestMapping(value = "/api/user")
public class UserRatingController {
	
	@Autowired
	private AdverService adverService;

	@Autowired(required = true)
	public void setAdverService(AdverService adverService) {
		this.adverService = adverService;
	}
	
	@RequestMapping(value="/{id}/rating", method = RequestMethod.POST)
	public ResponseEntity<User> addUserRating(@PathVariable("id") int uid, HttpSession session,@RequestParam("rating") int addedRating){
		User u = (User) session.getAttribute("logedUser");
		if(u==null){
	        return new ResponseEntity<> (HttpStatus.FORBIDDEN);
	    }
		if(u.getRole().getId()!=2){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User userRated = this.adverService.findUserById(uid);
		if(userRated==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(addedRating<=0){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(userRated.getId()==u.getId()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Set<UserRating> ratings = u.getUserRatings();
		for(UserRating ur : ratings){
			if(ur.getUser_rated().getId()==userRated.getId()){
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		
		Set<UserRating> allUserRatings = userRated.getUserRatings();
		int count = allUserRatings.size()+1;
		int sum = 0;
		for(UserRating r : allUserRatings){
			sum+=r.getRating();
		}
		sum+=addedRating;
		double updatedRating = sum/count;
		userRated.setUser_rate(updatedRating);
		UserRating userRatingg = new UserRating();
		userRatingg.setUser_rated(userRated);
		userRatingg.setUser(u);
		userRatingg.setRating(addedRating);
		this.adverService.addUserRating(userRatingg);
		
		userRated.getUserRatedRatings().add(userRatingg);
		this.adverService.updateUser(userRated);
		u.getUserRatings().add(userRatingg);
		this.adverService.updateUser(u);
		
		return new ResponseEntity<User>(userRated, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}/rating", method = RequestMethod.GET)
	public ResponseEntity<List<UserRating>> getUserRatings(@PathVariable("id") int uid, HttpSession session){
		User u = (User) session.getAttribute("logedUser");
		if(u==null){
	        return new ResponseEntity<> (HttpStatus.FORBIDDEN);
	    }
		User a = this.adverService.findUserById(uid);
		if(a==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(a.getRole().getId()!=2){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Set<UserRating> allUserRatings = a.getUserRatedRatings();
		List<UserRating> ret = new ArrayList<UserRating>();
		ret.addAll(allUserRatings);
		return new ResponseEntity<List<UserRating>>(ret, HttpStatus.OK);
	}

}
