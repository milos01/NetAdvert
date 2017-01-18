package com.mmmp.netadvert.DAOImplementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.netadvert.DAO.AdvertRatingDAO;
import com.mmmp.netadvert.model.AdvertRating;
import com.mmmp.netadvert.model.Comment;

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

	@Override
	public AdvertRating getUserOfAdvertRaiting(int user_id, int advert_id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from AdvertRating a where a.user.id=:idU and a.advert.id=:idA");
		query.setParameter("idU",user_id);
		query.setParameter("idA",advert_id);
		List<AdvertRating> AdvertRatingList = query.list();
		AdvertRating ar = null;
		for(AdvertRating a:AdvertRatingList){
			ar = a;
		}
		return ar;
	}

}
