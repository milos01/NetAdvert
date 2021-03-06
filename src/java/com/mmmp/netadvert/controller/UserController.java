package com.mmmp.netadvert.controller;
import java.security.Principal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import com.mmmp.netadvert.model.*;
import com.mmmp.netadvert.DTO.UserDTO;
import com.mmmp.netadvert.service.AdverService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


/**
 * Created by milosandric on 19/11/2016.
 */


@RestController
public class UserController {
    private AdverService adverService;

    @Autowired(required = true)
    public void setAdverService(AdverService ps) {
        this.adverService = ps;
    }

    @RequestMapping(value="/api/userr", method=RequestMethod.GET)
    public Principal user(Principal user) {
        return user;
    }
    /**
     * This method is part of user rest service. Method will register user with params sent form form. By default user role will
     * be set to 'Regular user'. If role was not found method will response with 404 error and if user can not be registered method will response
     * with 400 error.
     * @param user params from form are mapped on this user object
     * @return  Http response 200 OK
     * @see User
     */
    @RequestMapping(value="/api/register",consumes = "application/json")
    public  ResponseEntity<Void> registerUser(@RequestBody UserDTO user){
        Role regular = this.adverService.findRole(2);
        if(regular != null) {
            User u = new User();
            u.setEmail(user.getEmail());
            u.setFirst_name(user.getFirst_name());
            u.setLast_name(user.getLast_name());
            u.setPassword(user.getPassword());
            u.setUser_rate(user.getUser_rate());
            u.setRole(regular);


            try {
                this.adverService.registerUser(u);
                return new ResponseEntity<>(HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }


        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * This method is part of user rest service. Method will log user with params sent form form.
     * If params sent from form are not valid method will response with 404 and 500 errors. After user
     * was found method will set user object on session.
     * @param email param name email sent from form
     * @param password param name password sent from form
     * @param session HttpSession object for setting user on session
     * @return Http resonse 200 OK
     * @see User
     */
    @RequestMapping(value="/api/login", method=RequestMethod.POST )
    public  ResponseEntity<Void> loginUser(@RequestParam(value="email") String email, @RequestParam(value="password") String password, HttpSession session){
        User user = this.adverService.findUser(email);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!user.getPassword().equals(password)){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        session.setAttribute("logedUser",user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method is part of user rest service. Method will update logged user with params sent form form.
     * If some params are null method will leave them as is. If logged user was not found method will return 404 response .
//     * @param r_id role id send from form
     * @param user  params from form are mapped on this user object
     * @param session HttpSession object for getting logged user
     * @return JSON formatted user updated object
     * @see User
     */
    @RequestMapping(value="/api/user", method=RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody UserDTO user,Principal p){
        System.err.print(user.getFirst_name()+" "+user.getLast_name() + user.getEmail());
            User us = (User)this.adverService.findUser(p.getName());
            if (user != null) {
                us.setFirst_name(user.getFirst_name());
                us.setLast_name(user.getLast_name());
                Role role = (Role) this.adverService.findRole(1);
                try {
                    this.adverService.updateUser(us);
                    return new ResponseEntity<User>(us, HttpStatus.OK);
                } catch (HibernateException e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    /**
     * This method is part of user rest service. Method will return list of all users registrated on application.
     * @return list of JSON formatted user objects
     * @see User
     */
    @RequestMapping(value="/api/allusers", method=RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers(){

        List<User> users = this.adverService.getAllUsers();
        return new ResponseEntity<List<User>>(users,HttpStatus.OK);
    }

    /**
     *
     * @param email
     * @return
     */
    @RequestMapping(value="/api/getuser", method=RequestMethod.GET)
    public ResponseEntity<User> getUser(@RequestParam (value="email") String email){

        User user = (User)this.adverService.findUser(email);
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }

    @RequestMapping(value="/api/getlikeUser", method=RequestMethod.GET)
    public ResponseEntity<User> getlikeUser(@RequestParam (value="email") String email){

        User user = (User)this.adverService.findlikeUser(email);
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }
    /**
     * This method is part of user rest service. Method will return user who is logged on application.
     * @param session
     * @return JSON formatted user object
     * @see User
     */
    @RequestMapping(value="/api/user", method=RequestMethod.GET)
    public ResponseEntity<User> getUser(HttpSession session){
        User luser = (User)session.getAttribute("logedUser");
        if(luser == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(luser,HttpStatus.OK);
    }

    /**
     *  This method is part of user rest service. Method will return all adverts form specific user.
     *  Only logged user can get his adverts otherwise method will respond with 400 response.
     * @param uid user id sent from url
     * @param session
     * @return Http response 200 OK
     * @see Advert
     */
    @RequestMapping(value="/api/user/{uid}/adverts", method=RequestMethod.GET)
	public ResponseEntity<List<Advert>> getAllUserAdverts(@PathVariable("uid") int uid, HttpSession session){

        User user = (User)this.adverService.findUserById(uid);


        if (user == null || user.getId() != uid){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User u = this.adverService.findUserById(uid);
        if (u != null){
            List<Advert> adverts = new ArrayList<Advert>();
            adverts.addAll(u.getAdverts());
            return new ResponseEntity<List<Advert>>(adverts, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}
    
    @RequestMapping(value="/api/user/realestates", method=RequestMethod.GET)
	public ResponseEntity<List<Realestate>> getAllUserRealestates(HttpSession session){

    	User user = (User)session.getAttribute("logedUser");
        user = this.adverService.findUser("milossm94@hotmail.com");
        if(user == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Advert> adverts = new ArrayList<Advert>();
        adverts.addAll(user.getAdverts());
        List<Realestate> realestates = new ArrayList<Realestate>();
        Realestate real;
        for(Advert a : adverts){
        	real = a.getRealestate();
        	if(!realestates.contains(real)){
        		realestates.add(real);
        	}
        }
        return new ResponseEntity<List<Realestate>>(realestates, HttpStatus.OK);
	}

    @RequestMapping(value="/api/user/{uid}/mycompany", method=RequestMethod.GET)
    public ResponseEntity<Company> getUserCompany(@PathVariable("uid") int uid){

        Company cmp = this.adverService.findUserCompany(uid);
        if (cmp != null){
            return new ResponseEntity<Company>(cmp, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/api/user/{id}/isAvailable", method = RequestMethod.GET)
    private ResponseEntity<CompanyStaffs> isAvailable(@PathVariable("id") int id){
        CompanyStaffs cmp = (CompanyStaffs) this.adverService.findUserrCompany(id);
        return new ResponseEntity<CompanyStaffs>(cmp, HttpStatus.OK);
    }

    /**
     *  This method is part of user rest service. This method will update expire date of some advert. User and Advert can't be null
     *  User must be logged in to run this method properly. If user is logged and he is the one who created advert method will extend expire date for 1 month otherwise
     *  will respond with 400 response
     * @param session
     * @param uid user id sent from url
     * @param aid advert id sent from url
     * @return Http response 200 OK
     * @see Advert
     */
    @RequestMapping(value="/api/user/{uid}/advert/{aid}/expiredate", method=RequestMethod.PUT)
    public ResponseEntity<Advert> updateAdvertExpireDate(Principal u, @PathVariable("uid") int uid, @PathVariable("aid") int aid){
        User user = this.adverService.findUser(u.getName());
        if(user == null || user.getId()!=uid){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Advert a = this.adverService.findAdvert(aid);
        if (a != null){
            if(a.getUser().getId()!=user.getId()){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if(a.getDeleted()==true || a.getIs_sold()==true){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Date d = a.getExpire_date();
            Date currentDate = new Date();
            currentDate.setDate(currentDate.getDate()+7);
            if(currentDate.compareTo(d)>=0){
                d.setMonth(d.getMonth()+1);
                a.setExpire_date(d);
            }
            else{
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.adverService.updateAdvert(a);
    	return new ResponseEntity<Advert>(a, HttpStatus.OK);
    }
    @RequestMapping(value="/api/user/{uid}", method=RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable("uid") int id){
    	User user = (User)this.adverService.findUserById(id);
    	if (user==null){
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	return new ResponseEntity<User>(user,HttpStatus.OK);
    }
}
