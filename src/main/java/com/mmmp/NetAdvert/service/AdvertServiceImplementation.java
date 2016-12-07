package com.mmmp.NetAdvert.service;

import java.util.List;

import javax.transaction.Transactional;

import com.mmmp.NetAdvert.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmmp.NetAdvert.DAO.AdvertDAO;
import com.mmmp.NetAdvert.DAO.CommentDAO;
import com.mmmp.NetAdvert.DAO.CompanyDAO;
import com.mmmp.NetAdvert.DAO.LocationDAO;
import com.mmmp.NetAdvert.DAO.PictureDAO;
import com.mmmp.NetAdvert.DAO.RealestateDAO;
import com.mmmp.NetAdvert.DAO.ReportDAO;
import com.mmmp.NetAdvert.DAO.RoleDAO;
import com.mmmp.NetAdvert.DAO.TestDAO;
import com.mmmp.NetAdvert.DAO.UserDAO;
import com.mmmp.NetAdvert.model.Realestate;
import com.mmmp.NetAdvert.model.RealestateCategory;
import com.mmmp.NetAdvert.model.Report;
import com.mmmp.NetAdvert.model.Role;

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

	@Autowired
	private RealestateDAO realestateDAO;

	@Autowired
	private PictureDAO pictureDAO;

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

	@Override
	@Transactional
	public User updateUser(User luser, User user, Role role) {
		return this.userDAO.updateUser(luser, user, role);
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
		return this.adverDAO.addAdvert(a);
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
	public void addCompanyStaff(CompanyStaffs cs) {
		this.companyDAO.addCompanyStaff(cs);
	}


}
