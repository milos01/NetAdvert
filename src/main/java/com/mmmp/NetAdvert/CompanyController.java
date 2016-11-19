package com.mmmp.NetAdvert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mmmp.NetAdvert.model.CompanyStaffs;
import com.mmmp.NetAdvert.service.AdverService;

@RestController
@RequestMapping(value="api/company")
public class CompanyController {

	private AdverService adverService;
	
	@Autowired(required = true)
	@Qualifier(value = "adverService")
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
	
}
