package com.mmmp.netadvert.service;

import java.util.ArrayList;
import java.util.List;

import com.mmmp.netadvert.DTO.SearchDTO;
import com.mmmp.netadvert.model.*;


public interface AdverService {
	
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
	
	public Boolean updateUser(User luser);
	
	public Location checkForExistingLocation(String street, int street_number, String region, String city,
											 int postal_code);
	
	public boolean checkExistingCategoryType(int category_id, int type_id);
	
	public RealestateCategory findRealestateCategory(int category_id);
	
	public Realestate addRealestate(Realestate rs);
	
	public Realestate updateRealestate(Realestate rs);
	
	public Realestate findRealestate(int id);
	
	public Advert addAdvert(Advert a);
	
	public Report updateReport(Report report);
	
	public Report findReport(int id);
	
	public List<RealestateType> findAllRealstateTypes();
	
	public RealestateType findRealestateType(String name);
	
	public List<RealestateCategory> findAllRealestateCategory();
	
	public RealestateCategory findRealestateCategory(String name);
	
	public List<TechnicalEquipment> allEquipment();

	public void addPicture(Picture i);
	
	public void deletePicture(Picture i);
	
	public Picture findPicture(int id);
	
	public RealestateType findRealestateTypeById(int id);
	
	public TechnicalEquipment findTechnicalEquipmentById(int id);

	public  List<User> getAllUsers();

	public User findUserByCreds(String username, String password);

	public User findUserById(int id);

	public Company addCompany(Company company);

	public Company findCompany(int cid);

	public CompanyStaffs addCompanyStaff(CompanyStaffs cs);
	
	public Picture findPictureByName(String name);
	
	public Advert updateAdvert(Advert a);
	
	public boolean deleteAdvert(Advert a);
	
	public List<Advert> findAdvertByName(String name);
	
	public List<Advert> allAdverts();
	
	public AdvertRating addAdvertRating(AdvertRating a);

	public UserRating addUserRating(UserRating a);

	public List<CompanyStaffs> allCompanyStaff();
	
	public AdvertRating updateAdvertRating(AdvertRating a);
	
	public void deleteAdvertRating(AdvertRating a);
	
	public UserRating updateUserRating(UserRating ur);
	
	public void deleteUserRating(UserRating ur);
	
	public List<Advert> searchAdverts(SearchDTO search, List<TechnicalEquipment> tech);

    public Picture getAdvertMainPicture(int id);

    public User findlikeUser(String email);

    public  List<Company> findAllCompanys();

    public Company findUserCompany(int uid);

    public List<CompanyStaffs> staffForCompany(int cid);
}
