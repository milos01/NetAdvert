package com.mmmp.netadvert.DAOImplementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.netadvert.DAO.LocationDAO;
import com.mmmp.netadvert.model.Location;

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
		session.save(location);
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

	@Override
	public Location checkForExistingLocation(String street, int street_number, String region, String city,
			int postal_code) {
		street = street.trim();
		region = region.trim();
		city = city.trim();
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Location l where l.street=:street and l.streetNumber=:street_number and l.region=:region and l.city=:city and l.postalCode=:postal_code");
		query.setParameter("street", street);
		query.setParameter("street_number", street_number);
		query.setParameter("region", region);
		query.setParameter("city", city);
		query.setParameter("postal_code", postal_code);
		List<Location> locationList = query.list();
		Location location = null;
		for(Location a : locationList){
			location = a;
		}
		return location;
	}

	
	
	

}
