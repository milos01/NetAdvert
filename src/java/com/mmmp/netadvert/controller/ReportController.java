package com.mmmp.netadvert.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmmp.netadvert.model.Advert;
import com.mmmp.netadvert.model.Report;
import com.mmmp.netadvert.model.User;
import com.mmmp.netadvert.service.AdverService;

@RestController
@RequestMapping(value="api/report")
public class ReportController {

	@Autowired
	private AdverService adverService;
	
	@Autowired(required = true)
	public void setAdverService(AdverService ps) {
		this.adverService = ps;
	}
	
	/**
	 * Method creates a new report for a particular ad. If the ad exists, a report is created and returned 200 ok.
	 *  If an ad does not exist returns a 404 not found.
	 * @param text - text of the report
	 * @param advert_id - id of advert that are reported as not suitable
	 * @return Report object, Http response 200 ok
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Report> newReport(@RequestParam("reportDescription") String text,@RequestParam("advert_id") int advert_id){
//		User u = (User) session.getAttribute("logedUser");
		User u = this.adverService.findUser("doslicmm@live.com");
		Advert advert = this.adverService.findAdvert(advert_id);
		
		if (advert==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Report rep = new Report();
		rep.setAdvert(advert);
		rep.setUser(u);
		rep.setReportDescription(text);
		rep.setVerified(0);
		this.adverService.addNewReport(rep);
		
		return new ResponseEntity<Report>(rep,HttpStatus.OK);
	}
	
	/**
	 * Method will return the list of reports for all registered adverts.
	 * @return List of reports, Http response 200 ok
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Report>> allReports(){
		List<Report> list = this.adverService.reportList();
		for(Report r:list){
			System.out.println(r.getAdvert().getDescription());
		}
		return new ResponseEntity<List<Report>>(list,HttpStatus.OK);
	}
	
	/**
	 * Method of verifing an reported advert as accepted or not.
	 * @param id - id of report
	 * @param verify - the eligibility status
	 * @return updated Report object, Http response 200 ok
	 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ResponseEntity<Report> updateReport(@RequestParam("report_id") int id,@RequestParam("verify") int verify){
		
		Report r = this.adverService.findReport(id);
		r.setVerified(verify);
		this.adverService.updateReport(r);
		return new ResponseEntity<>(r,HttpStatus.OK);
	}
}
