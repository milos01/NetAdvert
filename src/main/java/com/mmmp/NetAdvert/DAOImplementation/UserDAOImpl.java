package com.mmmp.NetAdvert.DAOImplementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.NetAdvert.DAO.UserDAO;
import com.mmmp.NetAdvert.model.Advert;
import com.mmmp.NetAdvert.model.User;

@Repository
public class UserDAOImpl implements UserDAO {

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	@Override
	public User findUser(String email) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from User u where u.email=:email");
		query.setParameter("email",email);
		List<User> userList = query.list();
		User user = null;
		for(User a:userList){
			user = a;
		}
		return user;
	}

}
