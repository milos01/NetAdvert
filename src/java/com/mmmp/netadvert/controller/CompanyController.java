package com.mmmp.netadvert.controller;

import com.mmmp.netadvert.model.Advert;
import com.mmmp.netadvert.model.Company;
//import com.sun.tools.javac.code.Attribute;
//import com.sun.tools.javac.main.Main;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mmmp.netadvert.model.CompanyStaffs;
import com.mmmp.netadvert.model.User;
import com.mmmp.netadvert.service.AdverService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="api/company")
public class CompanyController {

	private AdverService adverService;
	
	@Autowired(required = true)
	public void setAdverService(AdverService ps) {
		this.adverService = ps;
	}
	
	@RequestMapping(value="/{id}/{idComp}",method=RequestMethod.GET)
	public ResponseEntity<CompanyStaffs> acceptUser(@PathVariable("id") int id,@PathVariable("idComp") int idComp){
		
		CompanyStaffs cs = this.adverService.getUserOfCompany(id, idComp);
		cs.setAccepted(1);
		this.adverService.updateCompanyStaff(cs);
		
		return new ResponseEntity<CompanyStaffs>(cs,HttpStatus.OK);
	}

    /**
     * This method is part of company rest service. Method will return company if it is found otherwise will respond with 404 response.
     * @param cid company id send from url
     * @return Http resonse 200 OK
     * @see Company
     */
    @RequestMapping(value="/{cid}", method = RequestMethod.GET)
    public ResponseEntity<Company> getCompany(@PathVariable("cid") int cid){

        Company cmp = (Company)this.adverService.findCompany(cid);
        if (cmp != null){
            System.err.print(cmp);
            return new ResponseEntity<Company>(cmp, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method is part of company rest service. Method will add new company from params sent in form.
     * If same company_name is passed as one in database, method will response with 500 response. After user
     * was found and company persisted method will return company object.
     * @param id user id send from form
     * @param company params from form are mapped on this company object
     * @return Http resonse 200 OK
     * @see Company
     */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Company> addCompany(@RequestParam(value = "u_id") int id, Company company){
            User usr = (User)this.adverService.findUserById(id);
            if(usr != null){
                Company comp = new Company();
                comp.setUser(usr);
                comp.setCompany_name(company.getCompany_name());

                try{
                    Company c = this.adverService.addCompany(comp);
                    return new ResponseEntity<Company>(c, HttpStatus.OK);
                }catch (ConstraintViolationException e){
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

	}
	/**
	 * This method is part of company rest service. Method will add new user for company.
	 * If same user and company are not found, method will response with 404 response. After user and company
	 * were found will save save user as worker in that company.
	 * @param cid company id send from url
	 * @param uid user id send from url
	 * @return Http resonse 200 OK
	 * @see CompanyStaffs
	 */
	@RequestMapping(value="/{cid}/user/{uid}", method = RequestMethod.POST)
	public ResponseEntity<Company> addUserOnCompany(@PathVariable("cid") int cid,@PathVariable("uid") int uid){
		System.err.print("usp-1");
		User usr = (User)this.adverService.findUserById(uid);
		System.err.print("usp0");
		Company cmp = (Company)this.adverService.findCompany(cid);
		if (cmp  != null && usr != null){
			System.err.print("usp");
			CompanyStaffs cs = new CompanyStaffs();
			cs.setUser(usr);
			cs.setAccepted(0);
			cs.setCompany(cmp);
			try{
				this.adverService.addCompanyStaff(cs);

				return new ResponseEntity<>(HttpStatus.OK);
			}catch (ConstraintViolationException e){
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

    /**
     * This method is part of company rest service. Method will list add users for some company.
     * If same company hasn't been found, method will response with 404 response. After company
     * was found method will return list of all users on that company.
     * @param cid user id send from form
     * @return Http resonse 200 OK
     * @see CompanyStaffs
     */
    @RequestMapping(value="/{cid}/allusers", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsersOnCompany(@PathVariable("cid") int cid){
        Company cmp = (Company)this.adverService.findCompany(cid);
        if (cmp != null) {
            List<User> users = new ArrayList<User>();

            for (CompanyStaffs item : cmp.getCs()) {
                users.add(item.getUser());

            }
            System.err.print(users);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

	
}
