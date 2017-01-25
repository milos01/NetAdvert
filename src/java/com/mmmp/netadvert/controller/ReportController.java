package com.mmmp.netadvert.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mmmp.netadvert.DTO.VerifyReportDTO;
import com.mmmp.netadvert.DTO.newReportDTO;
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
	public ResponseEntity<Report> newReport(@RequestBody newReportDTO repo,Principal user){
		User u = this.adverService.findUser(user.getName());
		
		Advert advert = this.adverService.findAdvert(repo.getAdvert_id());
		
		if (advert==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (repo.getReportDescription().equals("")||repo.getReportDescription()==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Report rep = new Report();
		rep.setAdvert(advert);
		rep.setUser(u);
		rep.setReportDescription(repo.getReportDescription());
		rep.setVerified(0);
		rep.setVisited(0);
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

		return new ResponseEntity<List<Report>>(list,HttpStatus.OK);
	}
	
	/**
	 * Method of verifing an reported advert as accepted or not.
	 * @param id - id of report
	 * @param verify - the eligibility status
	 * @return updated Report object, Http response 200 ok
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Report> updateReport(@RequestBody VerifyReportDTO verReport,Principal user){
		User u = this.adverService.findUser(user.getName());
		if (u.getRole().getName().equals("Verifier")){
			System.out.println("id: "+verReport.getReport_id() + " ver "+verReport.getVerify() );
			Report r = this.adverService.findReport(verReport.getReport_id());
			if (r==null){
				System.out.println("r null");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			if (r.getVisited()==1){
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			if (r.getVerified()==1){
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			r.setVerified(verReport.getVerify());
			r.setVisited(1);
			if (verReport.getVerify()==1){
				r.getAdvert().setDeleted(true);
				this.adverService.updateAdvert(r.getAdvert());
			}
			this.adverService.updateReport(r);
			List<Report> repList = this.adverService.findReportsByAdvert(r.getAdvert().getId());
			
			for(Report rep : repList){
				if(verReport.getReport_id()!=rep.getId()){
					rep.setVisited(1);
					rep.setVerified(verReport.getVerify());
					this.adverService.updateReport(rep);
				}
			}
			
			return new ResponseEntity<>(r,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
}
