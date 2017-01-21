package com.mmmp.netadvert.DAOImplementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.netadvert.DAO.PictureDAO;
import com.mmmp.netadvert.model.Picture;

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

	@Override
	public Picture findPictureByName(String name) {
		Session session = this.sessionFactory.getCurrentSession();		
		Query query=session.createQuery("from Picture p where p.pictureName=:name");
		query.setParameter("name", name);
		List<Picture> list=query.list();
		Picture p = null;
		for(Picture pic : list){
			p = pic;
		}
		return p;
	}

	@Override
	public Picture getAdvertMainPicture(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Picture p where p.advert.id=:id and p.isProfile=:ip");
		query.setParameter("id",id);
		query.setParameter("ip",true);
		List<Picture> list=query.list();
		Picture p = null;
		for(Picture pic : list){
			p = pic;
		}
		return p;
	}

	@Override
	public List<Picture> getAdvertPictures(int aid) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Picture p where p.advert.id=:id");
		query.setParameter("id",aid);
		List<Picture> list = query.list();
		return list;
	}

}
