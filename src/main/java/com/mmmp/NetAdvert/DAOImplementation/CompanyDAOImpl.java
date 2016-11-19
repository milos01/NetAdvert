package com.mmmp.NetAdvert.DAOImplementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.NetAdvert.DAO.CompanyDAO;
import com.mmmp.NetAdvert.model.CompanyStaffs;
import com.mmmp.NetAdvert.model.User;
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

}
