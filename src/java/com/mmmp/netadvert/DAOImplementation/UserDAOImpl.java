package com.mmmp.netadvert.DAOImplementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mmmp.netadvert.DAO.UserDAO;
import com.mmmp.netadvert.model.User;
import org.springframework.stereotype.Repository;

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
	public Boolean updateUser(User luser) {
		Session session = this.sessionFactory.getCurrentSession();
		session.merge(luser);
		return true;
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

    @Override
    public User findUserById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User u where u.id=:id");
        query.setParameter("id",id);
        List<User> userList = query.list();
        User user = null;
        for(User a:userList){
            user = a;
        }
        return user;
    }

}
