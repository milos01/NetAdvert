package com.mmmp.netadvert.controller;
import java.util.List;

import com.mmmp.netadvert.model.Role;
import com.mmmp.netadvert.model.User;
import com.mmmp.netadvert.DTO.UserDTO;
import com.mmmp.netadvert.service.AdverService;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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

    /**
     * This method is part of user rest service. Method will register user with params sent form form. By default user role will
     * be set to 'Regular user'. If role was not found method will response with 404 error and if user can not be registered method will response
     * with 400 error.
     * @param user params from form are mapped on this user object
     * @return  Http response 200 OK
     * @see User
     */
    @RequestMapping(value="/api/register", method=RequestMethod.POST)
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
    public ResponseEntity<User> updateUser(@RequestBody UserDTO user, HttpSession session){

        User luser = (User) session.getAttribute("logedUser");

        if (luser != null) {
            luser.setEmail(user.getEmail());
            luser.setFirst_name(user.getFirst_name());
            luser.setLast_name(user.getLast_name());
            luser.setPassword(user.getPassword());
            luser.setUser_rate(user.getUser_rate());

            Role role = (Role)this.adverService.findRole(1);
            try {
                this.adverService.updateUser(luser);
                return new ResponseEntity<User>(luser, HttpStatus.OK);
            }catch(HibernateException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }


        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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

    @RequestMapping(value="/api/user", method=RequestMethod.GET)
    public ResponseEntity<User> getUser(HttpSession session){
        User luser = (User)session.getAttribute("logedUser");
        if(luser == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(luser,HttpStatus.OK);
    }
    
}
