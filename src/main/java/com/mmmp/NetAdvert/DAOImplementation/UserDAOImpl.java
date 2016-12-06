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
import com.mmmp.NetAdvert.model.Role;

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

	@Override
	public Boolean RegisterUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(user);
		return true;
	}

	@Override
	public User updateUser(User luser, User user, Role role) {
		Session session = this.sessionFactory.getCurrentSession();
        luser.setRole(role);
        luser.setFirst_name(user.getFirst_name());
        luser.setLast_name(user.getLast_name());
		session.merge(luser);
		return luser;
	}

	@Override
	public List<User> getAllUsers() {
		Session session = this.sessionFactory.getCurrentSession();

		List<User> userList = session.createCriteria(User.class).list();
        return userList;
	}

    @Override
    public User findUserByCreds(String username, String password) {
//        Session session = this.sessionFactory.getCurrentSession();
//        Query query = session.createQuery("from User u where u.email=:email");
//        query.setParameter("email",email);
//        List<User> userList = query.list();
//        User user = null;
//        for(User a:userList){
//            user = a;
//        }
        return null;
    }

}
