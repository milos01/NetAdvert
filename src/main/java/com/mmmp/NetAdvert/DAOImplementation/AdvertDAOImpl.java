package com.mmmp.NetAdvert.DAOImplementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.NetAdvert.DAO.AdvertDAO;
import com.mmmp.NetAdvert.model.Advert;
@Repository
public class AdvertDAOImpl implements AdvertDAO {

	
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	@Override
	public Advert findAdvert(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Advert u where u.id=:id");
		query.setParameter("id",id);
		List<Advert> advertList = query.list();
		Advert advert = null;
		for(Advert a:advertList){
			advert = a;
		}
		return advert;
	}

	@Override
	public Advert addAdvert(Advert a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(a);
		return a;
	}

}
