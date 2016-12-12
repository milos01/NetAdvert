package com.mmmp.netadvert.DAOImplementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.netadvert.DAO.CommentDAO;
import com.mmmp.netadvert.model.Comment;
@Repository
public class CommentDAOImpl implements CommentDAO {
	
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	
	@Override
	public void createComment(Comment comment) {
		Session session  = this.sessionFactory.getCurrentSession();
		session.persist(comment);
	}


	@Override
	public void deleteComment(Comment commentId) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(commentId);
//		Query query = session.createQuery("delete FROM Comment c where c.id=:id");
//		query.setParameter("id",commentId);
	}


	@Override
	public Comment findComment(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Comment u where u.id=:id");
		query.setParameter("id",id);
		List<Comment> CommentList = query.list();
		Comment comm = null;
		for(Comment a:CommentList){
			comm = a;
		}
		return comm;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> allCommentsOfAdvert(int advert_id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Comment u where u.advert.id=:id");
		query.setParameter("id",advert_id);
		List<Comment> CommentList = query.list();
		return CommentList;
	}

}
