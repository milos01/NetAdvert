package com.mmmp.NetAdvert.service;

import java.util.List;

import javax.transaction.Transactional;

import com.mmmp.NetAdvert.DAO.*;
import com.mmmp.NetAdvert.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmmp.NetAdvert.DAO.AdvertDAO;
import com.mmmp.NetAdvert.DAO.CommentDAO;
import com.mmmp.NetAdvert.DAO.CompanyDAO;
import com.mmmp.NetAdvert.DAO.TestDAO;
import com.mmmp.NetAdvert.DAO.UserDAO;

@Service
public class AdvertServiceImplementation implements AdverService {

	
	@Autowired
	private TestDAO td;
	
	@Autowired
	private AdvertDAO adverDAO;
	
	@Autowired
	private CommentDAO commnetDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private CompanyDAO companyDAO;

	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private ReportDAO reportDAO;
	
	@Autowired
	private LocationDAO locationDAO;
	
	@Override
	@Transactional
	public void insert(Customer customer) {
		this.td.insert(customer);
		
	}

	@Override
	@Transactional
	public Advert findAdvert(int id) {
		return this.adverDAO.findAdvert(id);
	}

	@Override
	@Transactional
	public void createComment(Comment comment) {
		this.commnetDAO.createComment(comment);
	}

	@Override
	@Transactional
	public User findUser(String email) {
		return this.userDAO.findUser(email);
	}

	@Override
	@Transactional
	public void deleteComment(Comment commentId) {
		this.commnetDAO.deleteComment(commentId);
	}

	@Override
	@Transactional
	public Comment findComment(int id) {
		return this.commnetDAO.findComment(id);
	}

	@Override
	@Transactional
	public CompanyStaffs getUserOfCompany(int user_id, int company_id) {
        return this.companyDAO.getUserOfCompany(user_id, company_id);

    }
    @Override
    @Transactional
	public Boolean registerUser(User user) {
		return this.userDAO.RegisterUser(user);
	}

	@Override
	@Transactional
	public void updateCompanyStaff(CompanyStaffs cs) {
        this.companyDAO.updateCompanyStaff(cs);
    }

    @Override
    @Transactional
	public Role findRole(int id) {
		return this.roleDAO.findRole(id);
	}

	@Override
	@Transactional
	public List<Comment> allCommentsOfAdvert(int advert_id) {
		return this.commnetDAO.allCommentsOfAdvert(advert_id);
	}

	@Override
	@Transactional
	public Report addNewReport(Report report) {
		return this.reportDAO.addNewReport(report);
	}

	@Override
	@Transactional
	public List<Report> reportList() {
		return this.reportDAO.reportList();
	}

	@Override
	@Transactional
	public void createLocation(Location location) {
		this.locationDAO.createLocation(location);
	}

	@Override
	@Transactional
	public Location findLocation(int id) {
		return this.locationDAO.findLocation(id);
	}

	@Override
	@Transactional
	public void updateLocation(Location location) {
		this.locationDAO.updateLocation(location);
	}


}
