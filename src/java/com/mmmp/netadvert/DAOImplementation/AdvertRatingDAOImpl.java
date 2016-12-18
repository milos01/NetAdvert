package com.mmmp.netadvert.DAOImplementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.netadvert.DAO.AdvertRatingDAO;
import com.mmmp.netadvert.model.AdvertRating;

@Repository
public class AdvertRatingDAOImpl implements AdvertRatingDAO{
	
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public AdvertRating addAdvertRating(AdvertRating a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(a);
		return a;
	}

	@Override
	public AdvertRating updateAdvertRating(AdvertRating a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.merge(a);
		return a;
	}

	@Override
	public void deleteAdvertRating(AdvertRating a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(a);	}

}
