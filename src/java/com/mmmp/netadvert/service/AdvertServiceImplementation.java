package com.mmmp.netadvert.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.mmmp.netadvert.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mmmp.netadvert.DAO.AdvertDAO;
import com.mmmp.netadvert.DAO.AdvertRatingDAO;
import com.mmmp.netadvert.DAO.CommentDAO;
import com.mmmp.netadvert.DAO.CompanyDAO;
import com.mmmp.netadvert.DAO.LocationDAO;
import com.mmmp.netadvert.DAO.PictureDAO;
import com.mmmp.netadvert.DAO.RealestateDAO;
import com.mmmp.netadvert.DAO.ReportDAO;
import com.mmmp.netadvert.DAO.RoleDAO;
import com.mmmp.netadvert.DAO.UserDAO;
import com.mmmp.netadvert.DAO.UserRatingDAO;
import com.mmmp.netadvert.DTO.SearchDTO;

@Service
public class AdvertServiceImplementation implements AdverService {

	@Autowired
	private AdvertDAO advertDAO;

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

	@Autowired
	private RealestateDAO realestateDAO;

	@Autowired
	private PictureDAO pictureDAO;
	
	@Autowired
	private AdvertRatingDAO advertRatingDAO;
	
	@Autowired
	private UserRatingDAO userRatingDAO;


	@Override
	@Transactional
	public Advert findAdvert(int id) {
		return this.advertDAO.findAdvert(id);
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
	public void deleteComment(int commentId) {
		Comment c =this.commnetDAO.findComment(commentId);
		this.commnetDAO.deleteComment(c);
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

	@Override
	@Transactional
	public Boolean updateUser(User luser) {
		return this.userDAO.updateUser(luser);
	}

	@Override
	@Transactional
	public Location checkForExistingLocation(String street, int street_number, String region, String city,
			int postal_code) {
		return this.locationDAO.checkForExistingLocation(street, street_number, region, city, postal_code);
	}

	@Override
	@Transactional
	public boolean checkExistingCategoryType(int category_id, int type_id) {
		return this.realestateDAO.checkExistingCategoryType(category_id, type_id);
	}

	@Override
	@Transactional
	public RealestateCategory findRealestateCategory(int category_id) {
		return this.realestateDAO.findRealestateCategory(category_id);
	}

	@Override
	@Transactional
	public Realestate addRealestate(Realestate rs) {
		return this.realestateDAO.addRealestate(rs);
	}

	@Override
	@Transactional
	public Realestate updateRealestate(Realestate rs) {
		return this.realestateDAO.updateRealestate(rs);
	}

	@Override
	@Transactional
	public Realestate findRealestate(int id) {
		return this.realestateDAO.findRealestate(id);
	}

	@Override
	@Transactional
	public Advert addAdvert(Advert a){
		return this.advertDAO.addAdvert(a);
	}

	@Override
	@Transactional
	public Report updateReport(Report report) {
		return this.reportDAO.updateReport(report);
	}

	@Override
	@Transactional
	public void addPicture(Picture i) {
		this.pictureDAO.addPicture(i);
	}

	@Override
	@Transactional
	public Report findReport(int id) {
		return this.reportDAO.findReport(id);
	}

	@Override
	@Transactional
	public void deletePicture(Picture i) {
		this.pictureDAO.deletePicture(i);
	}

	@Override
	@Transactional
	public List<RealestateType> findAllRealstateTypes() {
		return this.realestateDAO.findAllRealstateTypes();
	}

	@Override
	@Transactional
	public RealestateType findRealestateType(String name) {
		return this.realestateDAO.findRealestateType(name);
	}

	@Override
	@Transactional
	public List<RealestateCategory> findAllRealestateCategory() {
		return this.realestateDAO.findAllRealestateCategory();
	}

	@Override
	@Transactional
	public RealestateCategory findRealestateCategory(String name) {
		return this.realestateDAO.findRealestateCategory(name);
	}

	@Override
	@Transactional
	public List<TechnicalEquipment> allEquipment() {
		return this.realestateDAO.allEquipment();
	}

	@Override
	@Transactional
	public Picture findPicture(int id) {
		return this.pictureDAO.findPicture(id);
	}

	@Override
	@Transactional
	public RealestateType findRealestateTypeById(int id) {
		return this.realestateDAO.findRealestateTypeById(id);
	}

	@Override
	@Transactional
	public TechnicalEquipment findTechnicalEquipmentById(int id) {
		return this.realestateDAO.findTechnicalEquipmentById(id);
	}

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return this.userDAO.getAllUsers();
    }

    @Override
    @Transactional
    public User findUserByCreds(String username, String password) {
        return this.userDAO.findUserByCreds(username, password);
    }

	@Override
	@Transactional
	public User findUserById(int id) {
		return this.userDAO.findUserById(id);
	}

    @Override
    @Transactional
    public Company addCompany(Company company) {
        return this.companyDAO.addCompany(company);
    }

	@Override
	@Transactional
	public Company findCompany(int cid) {
		return this.companyDAO.findCompany(cid);
	}

	@Override
	@Transactional
	public CompanyStaffs addCompanyStaff(CompanyStaffs cs) {
		return this.companyDAO.addCompanyStaff(cs);
	}

	@Override
	@Transactional
	public Picture findPictureByName(String name) {
		return this.pictureDAO.findPictureByName(name);
	}

	@Transactional
	@Override
	public Advert updateAdvert(Advert a) {
		return this.advertDAO.updateAdvert(a);
	}

	@Override
	@Transactional
	public boolean deleteAdvert(Advert a) {
		return this.advertDAO.deleteAdvert(a);
	}

	@Override
	@Transactional
	public List<Advert> findAdvertByName(String name) {
		return this.advertDAO.findAdvertByName(name);
	}

	@Override
	@Transactional
	public Page<Advert> allAdvertsPage(Map<String, String> map, Pageable pageable) {
		return this.advertDAO.allAdvertsPage(map, pageable);
	}
	
	@Override
	@Transactional
	public List<Advert> allAdverts() {
		return this.advertDAO.allAdverts();
	}

	@Override
	@Transactional
	public AdvertRating addAdvertRating(AdvertRating a) {
		return this.advertRatingDAO.addAdvertRating(a);
	}

	@Override
	@Transactional
	public UserRating addUserRating(UserRating a) {
		return this.userRatingDAO.addUserRating(a);
	}

	@Override
	@Transactional
	public List<CompanyStaffs> allCompanyStaff() {
		return this.companyDAO.allCompanyStaff();
	}

	@Override
	@Transactional
	public AdvertRating updateAdvertRating(AdvertRating a) {
		return this.advertRatingDAO.updateAdvertRating(a);
	}

	@Override
	@Transactional
	public void deleteAdvertRating(AdvertRating a) {
		this.advertRatingDAO.deleteAdvertRating(a);
	}

	@Override
	@Transactional	
	public UserRating updateUserRating(UserRating ur) {
		return this.userRatingDAO.updateUserRating(ur);
	}

	@Override
	@Transactional
	public void deleteUserRating(UserRating ur) {
		this.userRatingDAO.deleteUserRating(ur);
	}

	@Override
	@Transactional
	public Page<Advert> searchAdverts(Map<String, Object> map, List<TechnicalEquipment> tech, Pageable pageable) {
		return this.advertDAO.searchAdverts(map, tech, pageable);
	}

	@Override
	@Transactional
	public Picture getAdvertMainPicture(int id) {
		return this.pictureDAO.getAdvertMainPicture(id);
	}

	@Override
	@Transactional
	public User findlikeUser(String email) {
		return this.userDAO.getlikeUser(email);
	}

	@Override
	@Transactional
	public List<Company> findAllCompanys() {
		return this.companyDAO.getAllCompanys();
	}

	@Override
	@Transactional
	public Company findUserCompany(int uid) {
		return this.userDAO.findUs1erCompany(uid);
	}

	@Override
	@Transactional
	public List<CompanyStaffs> staffForCompany(int cid) {
		return this.companyDAO.staffForCompany(cid);
	}
	
	@Override
	@Transactional
	public SoldAdvert addSoldAdvert(SoldAdvert s) {
		return this.advertDAO.addSoldAdvert(s);
	}

	@Override
	@Transactional
	public List<Report> findReportsByAdvert(int advert_id) {
		return this.reportDAO.findReportsByAdvert(advert_id);
	}

	@Override
	@Transactional
	public CompanyStaffs findUserrCompany(int uid) {
		return this.userDAO.findUserrCompany(uid);
	}
	
	@Override
	@Transactional
	public AdvertRating getUserOfAdvertRaiting(int user_id, int advert_id) {
		return this.advertRatingDAO.getUserOfAdvertRaiting(user_id, advert_id);
	}

	@Override
	@Transactional
	public UserRating getUserOfUserRaiting(int user_id, int user_idP) {
		return this.userRatingDAO.getUserOfUserRaiting(user_id, user_idP);
	}

	@Override
	@Transactional
	public List<Advert> getAllAdvertsOfUser(int user_id) {
		return this.advertDAO.getAllAdvertsOfUser(user_id);
	}


}
