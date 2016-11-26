package com.mmmp.NetAdvert.DAOImplementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.NetAdvert.DAO.ReportDAO;
import com.mmmp.NetAdvert.model.Report;

@Repository
public class ReportDAOImpl implements ReportDAO {

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	@Override
	public Report addNewReport(Report report) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(report);
		return  report;
	}

	@Override
	public List<Report> reportList() {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Report");
		List<Report> ReportList = query.list();
		return ReportList;
	}

}
