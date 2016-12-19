package com.mmmp.netadvert.DAOImplementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.netadvert.DAO.AdvertDAO;
import com.mmmp.netadvert.DTO.SearchDTO;
import com.mmmp.netadvert.model.Advert;
import com.mmmp.netadvert.model.TechnicalEquipment;
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

	@Override
	public Advert updateAdvert(Advert a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.merge(a);
		return a;
	}

	@Override
	public boolean deleteAdvert(Advert a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(a);
		return true;
	}

	@Override
	public List<Advert> findAdvertByName(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Advert u where u.name=:name");
		query.setParameter("name",name);
		List<Advert> advertList = query.list();
		return advertList;
	}

	@Override
	public List<Advert> allAdverts() {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Advert");
		List<Advert> advertList = query.list();
		return advertList;
	}

	@Override
	public List<Advert> searchAdverts(SearchDTO search, List<TechnicalEquipment> tech) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Advert a");
		return null;
	}

}
