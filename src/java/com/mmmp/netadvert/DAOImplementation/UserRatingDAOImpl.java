package com.mmmp.netadvert.DAOImplementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.netadvert.DAO.UserRatingDAO;
import com.mmmp.netadvert.model.UserRating;

@Repository
public class UserRatingDAOImpl implements UserRatingDAO{
	
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public UserRating addUserRating(UserRating u) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(u);
		return u;
	}

	@Override
	public UserRating updateUserRating(UserRating ur) {
		Session session = this.sessionFactory.getCurrentSession();
		session.merge(ur);
		return ur;
	}

	@Override
	public void deleteUserRating(UserRating ur) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(ur);
	}

}
