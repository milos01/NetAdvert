package com.mmmp.NetAdvert.DAOImplementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.SimpleSubqueryExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mmmp.NetAdvert.DAO.RealestateDAO;
import com.mmmp.NetAdvert.model.Realestate;
import com.mmmp.NetAdvert.model.RealestateCategory;
import com.mmmp.NetAdvert.model.RealestateType;
import com.mmmp.NetAdvert.model.TechnicalEquipment;

@Repository
public class RealestateDAOImpl implements RealestateDAO{
	
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	@Override 
	public RealestateCategory findRealestateCategory(int category_id){
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from RealestateCategory rc where rc.id=:category_id");
		query.setParameter("category_id", category_id);
		List<RealestateCategory> list = query.list();
		RealestateCategory r = null;
		for(RealestateCategory real : list){
			r = real;
		}
		return r;
	}

	@Override
	public boolean checkExistingCategoryType(int category_id, int type_id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("select rc.id, rc.categoryName from RealestateCategory rc join rc.types rt where rc.id=:category_id and rt.id=:type_id");
		query.setParameter("category_id", category_id);
		query.setParameter("type_id", type_id);
		List<RealestateCategory> list=query.list();
		RealestateCategory r = null;
		for(RealestateCategory real : list){
			r = real;
		}
		if(r==null)
			return false;
		return true;
	}

	@Override
	public Realestate addRealestate(Realestate rs) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(rs);
		return rs;
	}

	@Override
	public Realestate updateRealestate(Realestate rs) {
		Session session = this.sessionFactory.getCurrentSession();
		session.merge(rs);
		return rs;
	}

	@Override
	public Realestate findRealestate(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Realestate r where r.id=:id");
		query.setParameter("id", id);
		List<Realestate> list=query.list();
		Realestate r = null;
		for(Realestate real : list){
			r = real;
		}
		return r;
	}

	@Override
	public List<RealestateType> findAllRealstateTypes() {
		Session session = this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from RealestateType");
		List<RealestateType> list=query.list();
		return list;
	}

	@Override
	public RealestateType findRealestateType(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from RealestateType r where r.name=:name");
		query.setParameter("name", name);
		List<RealestateType> list=query.list();
		RealestateType r = null;
		for(RealestateType real : list){
			r = real;
		}
		return r;
	}

	@Override
	public List<RealestateCategory> findAllRealestateCategory() {
		Session session = this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from RealestateCategory");
		List<RealestateCategory> list=query.list();
		return list;
	}

	@Override
	public RealestateCategory findRealestateCategory(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from RealestateCategory r where r.categoryName=:name");
		query.setParameter("name", name);
		List<RealestateCategory> list=query.list();
		RealestateCategory r = null;
		for(RealestateCategory real : list){
			r = real;
		}
		return r;
	}

	@Override
	public List<TechnicalEquipment> allEquipment() {
		Session session = this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from TechnicalEquipment");
		List<TechnicalEquipment> list=query.list();
		return list;
	}

}
