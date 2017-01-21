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

import com.mmmp.netadvert.DTO.UserNewRate;
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
	
	/**
	 * This method is part of user rest service. It adds user rating
	 * @param uid user for which is being added rating, and it checks if user has permission to add rating.
	 * @param addedRating added rating
	 * @return Http status 200 OK
	 * @see User
	 */
	@RequestMapping(value="/{id}/rating", method = RequestMethod.POST)
	public ResponseEntity<User> addUserRating(@PathVariable("id") int uid, Principal user,@RequestBody UserNewRate addedRating){
		User u = this.adverService.findUser(user.getName());

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
		if(addedRating.getRate()<=0){
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
		sum+=addedRating.getRate();
		double updatedRating = sum/count;
		userRated.setUser_rate(updatedRating);
		UserRating userRatingg = new UserRating();
		userRatingg.setUser_rated(userRated);
		userRatingg.setUser(u);
		userRatingg.setRating(addedRating.getRate());
		this.adverService.addUserRating(userRatingg);
		
		userRated.getUserRatedRatings().add(userRatingg);
		this.adverService.updateUser(userRated);
		u.getUserRatings().add(userRatingg);
		this.adverService.updateUser(u);
		
		return new ResponseEntity<User>(userRated, HttpStatus.OK);
	}
	
	/**
	 * This method is part of advert rest service. It gets list of ratings of specified user.
	 * @param uid user id which ratings are being returned
	 * @return Http status 200 OK
	 * @see User, UserRating
	 */
	@RequestMapping(value="/{id}/rating", method = RequestMethod.GET)
	public ResponseEntity<List<UserRating>> getUserRatings(@PathVariable("id") int uid, Principal user){
		User u = this.adverService.findUser(user.getName());
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
	
	/**
	 * This method is part of advert rest service. It updates user rating.
	 * @param aid user id which rating is being updated
	 * @param addedRating updated rating
	 * @return Http status 200 OK
	 * @see User
	 */
	@RequestMapping(value="/{id}/rating", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUserRating(@PathVariable("id") int aid, Principal user, @RequestParam("rating") int addedRating){
		User u = this.adverService.findUser(user.getName());
		if(u==null){
	        return new ResponseEntity<> (HttpStatus.FORBIDDEN);
	    }
		User a = this.adverService.findUserById(aid);
		if(a==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(a.getRole().getId()!=2){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(addedRating<=0){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Set<UserRating> ratings = u.getUserRatings();
		boolean exists = false;
		UserRating updatedR = new UserRating();
		for(UserRating ar : ratings){
			if(ar.getUser_rated().getId()==a.getId()){
				exists = true;
				updatedR = ar;
				break;
			}
		}
		if(exists==false){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		updatedR.setRating(addedRating);
		this.adverService.updateUserRating(updatedR);
		
		
		a=this.adverService.findUserById(aid);
		Set<UserRating> allUserRatings = a.getUserRatedRatings();
		int count = allUserRatings.size();
		int sum = 0;
		for(UserRating r : allUserRatings){
			sum+=r.getRating();
		}
		double updatedRating = sum/count;
		a.setUser_rate(updatedRating);
		
		
		this.adverService.updateUser(a);
		
		return new ResponseEntity<User>(a, HttpStatus.OK);
	}
	
	/**
	 * This method is part of advert rest service. It deletes rating from specified user.
	 * @param aid user which rating is being deleted.
	 * @return Http status 200 OK
	 * @see User
	 */
	@RequestMapping(value="/{id}/rating", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUserRating(@PathVariable("id") int aid, Principal user){
		User u = this.adverService.findUser(user.getName());
		if(u==null){
	        return new ResponseEntity<> (HttpStatus.FORBIDDEN);
	    }
		User a = this.adverService.findUserById(aid);
		if(a==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(a.getRole().getId()!=2){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Set<UserRating> ratings = u.getUserRatings();
		boolean exists = false;
		UserRating deleteR = new UserRating();
		for(UserRating ar : ratings){
			if(ar.getUser_rated().getId()==a.getId()){
				exists = true;
				deleteR = ar;
				break;
			}
		}
		if(exists==false){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		this.adverService.deleteUserRating(deleteR);
		
		a=this.adverService.findUserById(aid);
		Set<UserRating> allUserRatings = a.getUserRatedRatings();
		int count = allUserRatings.size();
		if(count==0){
			a.setUser_rate(0);;
		}
		else{
			int sum = 0;
			for(UserRating r : allUserRatings){
				sum+=r.getRating();
			}
			double updatedRating = sum/count;
			a.setUser_rate(updatedRating);
		}
		this.adverService.updateUser(a);
		
		return new ResponseEntity<User>(a, HttpStatus.OK);
	}
	
	@RequestMapping(value="/findUserUserRait", method = RequestMethod.GET)
	public ResponseEntity<UserRating> getUserUserRate(@RequestParam("user_id") int idU,@RequestParam("user_idP") int idUP){
		UserRating ur = this.adverService.getUserOfUserRaiting(idU, idUP);
		if (ur==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserRating>(ur,HttpStatus.OK);
	}

}
