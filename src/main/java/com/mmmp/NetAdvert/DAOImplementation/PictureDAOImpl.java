package com.mmmp.NetAdvert.DAOImplementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.NetAdvert.DAO.PictureDAO;
import com.mmmp.NetAdvert.model.Picture;

@Repository
public class PictureDAOImpl implements PictureDAO {
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addPicture(Picture i) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(i);
	}

	@Override
	public void deletePicture(Picture i) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(i);
	}

	@Override
	public Picture findPicture(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Query query=session.createQuery("from Picture p where p.id=:id");
		query.setParameter("id", id);
		List<Picture> list=query.list();
		Picture p = null;
		for(Picture pic : list){
			p = pic;
		}
		return p;
	}

}
