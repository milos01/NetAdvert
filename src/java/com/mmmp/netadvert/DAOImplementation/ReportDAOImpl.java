package com.mmmp.netadvert.DAOImplementation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.netadvert.DAO.ReportDAO;
import com.mmmp.netadvert.model.Report;

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
		Query query = session.createQuery("from Report r where r.visited=:vis");
		query.setParameter("vis",0);
		List<Report> ReportList = query.list();
		if(ReportList == null){
			ReportList = new ArrayList<Report>();
		}
		return ReportList;
	}

	@Override
	public Report updateReport(Report report) {
		Session session = this.sessionFactory.getCurrentSession();
		session.merge(report);
		return report;
	}

	@Override
	public Report findReport(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Report u where u.id=:id");
		query.setParameter("id",id);
		List<Report> ReportList = query.list();
		Report rep = null;
		for(Report a:ReportList){
			rep = a;
		}
		return rep;
	}

	@Override
	public List<Report> findReportsByAdvert(int advert_id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Report r where r.advert.id=:id");
		query.setParameter("id",advert_id);
		List<Report> ReportList = query.list();
		if(ReportList == null){
			ReportList = new ArrayList<Report>();
		}
		return ReportList;
	}

}
