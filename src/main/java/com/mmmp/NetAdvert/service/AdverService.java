package com.mmmp.NetAdvert.service;

import java.util.List;

import com.mmmp.NetAdvert.model.*;


public interface AdverService {
	
	public  void insert(Customer cus);
	
	public Advert findAdvert(int id);
	
	public void createComment(Comment comment);
	
	public User findUser(String email);
	
	public void deleteComment(Comment commentId);
	
	public Comment findComment(int id);
	
	public CompanyStaffs getUserOfCompany(int user_id, int company_id);
	
	public void updateCompanyStaff(CompanyStaffs cs);

	public Boolean registerUser(User user);

	public Role findRole(int id);

	public List<Comment> allCommentsOfAdvert(int advert_id);
	
	public Report addNewReport(Report report);
	
	public List<Report> reportList();
	
	public void createLocation(Location location);

	public Location findLocation(int id);
	
	public void updateLocation(Location location);
}
