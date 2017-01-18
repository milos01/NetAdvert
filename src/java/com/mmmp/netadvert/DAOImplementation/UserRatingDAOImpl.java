package com.mmmp.netadvert.DAOImplementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.netadvert.DAO.UserRatingDAO;
import com.mmmp.netadvert.model.AdvertRating;
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

	@Override
	public UserRating getUserOfUserRaiting(int user_id, int user_idP) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from UserRating a where a.user.id=:idU and a.user_rated.id=:idA");
		query.setParameter("idU",user_id);
		query.setParameter("idA",user_idP);
		List<UserRating> UserRatingList = query.list();
		UserRating ar = null;
		for(UserRating a:UserRatingList){
			ar = a;
		}
		return ar;
	}

}
