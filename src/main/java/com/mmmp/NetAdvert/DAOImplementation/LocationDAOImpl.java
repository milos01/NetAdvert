package com.mmmp.NetAdvert.DAOImplementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.NetAdvert.DAO.LocationDAO;
import com.mmmp.NetAdvert.model.CompanyStaffs;
import com.mmmp.NetAdvert.model.Location;

@Repository
public class LocationDAOImpl implements LocationDAO {

	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	@Override
	public void createLocation(Location location) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(location);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Location findLocation(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Location l where l.id=:id");
		query.setParameter("id",id);
		List<Location> locationList = query.list();
		Location location = null;
		for(Location a:locationList){
			location = a;
		}
		return location;
	}

	@Override
	public void updateLocation(Location location) {
		Session session = this.sessionFactory.getCurrentSession();
		session.merge(location);
	}

}
