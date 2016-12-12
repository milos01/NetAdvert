package com.mmmp.netadvert.DAOImplementation;

import java.util.List;

import com.mmmp.netadvert.model.Company;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.netadvert.DAO.CompanyDAO;
import com.mmmp.netadvert.model.CompanyStaffs;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CompanyStaffs getUserOfCompany(int user_id, int company_id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from CompanyStaffs cs where cs.user.id=:id AND cs.company.id=:comp_id");
		query.setParameter("id",user_id);
		query.setParameter("comp_id", company_id);
		List<CompanyStaffs> staffs = query.list();
		CompanyStaffs companyStaffs = null;
		for(CompanyStaffs a:staffs){
			companyStaffs = a;
		}
		return companyStaffs;
	}

	@Override
	public void updateCompanyStaff(CompanyStaffs cs) {
		Session session = this.sessionFactory.getCurrentSession();
		session.merge(cs);
	}

	@Override
	public Company addCompany(Company company) {

		Session session = this.sessionFactory.getCurrentSession();
		session.persist(company);
		return company;
	}

	@Override
	public Company findCompany(int cid) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Company cs where cs.id=:id");
		query.setParameter("id",cid);
		List<Company> staffs = query.list();
		Company companyStaffs = null;
		for(Company a:staffs){
			companyStaffs = a;
		}
		return companyStaffs;
	}

	@Override
	public void addCompanyStaff(CompanyStaffs cs) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(cs);
	}

}
