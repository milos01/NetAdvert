package com.mmmp.netadvert.controller;

import com.mmmp.netadvert.model.Company;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mmmp.netadvert.model.CompanyStaffs;
import com.mmmp.netadvert.model.User;
import com.mmmp.netadvert.service.AdverService;

import java.sql.SQLException;

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
	 * @param cid user id send from form
	 * @param uid user id send from form
	 * @return Http resonse 200 OK
	 * @see CompanyStaffs
	 */
	@RequestMapping(value="/{cid}/user/{uid}", method = RequestMethod.POST)
	public ResponseEntity<Company> addUserOnCompany(@PathVariable("cid") int cid,@PathVariable("uid") int uid){
		User usr = (User)this.adverService.findUserById(uid);
		Company cmp = (Company)this.adverService.findCompany(cid);
		if (cmp  != null && usr != null){
			System.err.print(cmp.getCompany_name());
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

	
}
