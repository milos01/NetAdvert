package com.mmmp.NetAdvert.DAOImplementation;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.NetAdvert.DAO.TestDAO;
import com.mmmp.NetAdvert.model.Customer;

@Repository
public class TestDAOImpl implements TestDAO{

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	@Override
	public void insert(Customer customer) {
		Session session = this.sessionFactory.getCurrentSession();
		
		
		
		session.persist(customer);
		
	}

}
