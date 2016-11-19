package com.mmmp.NetAdvert;

import com.mmmp.NetAdvert.model.Comment;
import com.mmmp.NetAdvert.model.Customer;
import com.mmmp.NetAdvert.model.Role;
import com.mmmp.NetAdvert.model.User;
import com.mmmp.NetAdvert.service.AdverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



/**
 * Created by milosandric on 19/11/2016.
 */
@RestController
public class UserController {
    private AdverService adverService;

    @Autowired(required = true)
    @Qualifier(value = "adverService")
    public void setAdverService(AdverService ps) {
        this.adverService = ps;
    }

    @RequestMapping(value="/api/register", method=RequestMethod.POST)
    public  ResponseEntity<Void> registerUser(User user){
        Role regular = this.adverService.findRole(1);
        user.setRole(regular);
        System.out.print(user.toString());
        if (this.adverService.registerUser(user)){
            return new ResponseEntity<Void>(HttpStatus.OK);
        }else{
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
