package com.mmmp.netadvert.DAOImplementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mmmp.netadvert.model.Picture;
import com.mmmp.netadvert.model.SoldAdvert;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import com.mmmp.netadvert.DAO.AdvertDAO;
import com.mmmp.netadvert.DTO.SearchDTO;
import com.mmmp.netadvert.model.Advert;
import com.mmmp.netadvert.model.TechnicalEquipment;
@Repository
public class AdvertDAOImpl implements AdvertDAO {

	
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	@Override
	public Advert findAdvert(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Advert u where u.id=:id");
		query.setParameter("id",id);
		List<Advert> advertList = query.list();
		Advert advert = null;
		for(Advert a:advertList){
			advert = a;
		}
		return advert;
	}

	@Override
	public Advert addAdvert(Advert a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(a);
		return a;
	}

	@Override
	public Advert updateAdvert(Advert a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.merge(a);
		return a;
	}

	@Override
	public boolean deleteAdvert(Advert a) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(a);
		return true;
	}

	@Override
	public List<Advert> findAdvertByName(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Advert u where u.name=:name");
		query.setParameter("name",name);
		List<Advert> advertList = query.list();
		return advertList;
	}

	@Override
	public Page<Advert> allAdvertsPage(Map<String, String> map, Pageable pageable) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(Advert.class, "advert");
		Projection idCountProjection = Projections.countDistinct("advert.id");
		cr.setProjection(idCountProjection);
		cr.add(Restrictions.eq("advert.isDeleted", Boolean.FALSE));
		int totalResultCount = ((Long)cr.uniqueResult()).intValue();
		if(totalResultCount==0){
			pageable = new PageRequest(0, 1, null);
			Page<Advert> page = new PageImpl<Advert>(new ArrayList<Advert>(), pageable, totalResultCount);
			return page;
		}
		else{
			cr.setProjection(Projections.distinct(Projections.property("advert.id")));
			if(map.get("size")==null){
				cr.setMaxResults(12);
				cr.setFirstResult(pageable.getOffset());
				pageable = new PageRequest(pageable.getPageNumber(), 12, pageable.getSort());
			}
			else{
				cr.setMaxResults(pageable.getPageSize());
				cr.setFirstResult(pageable.getOffset());
			}
			if(pageable.getSort()==null){
				cr.addOrder(org.hibernate.criterion.Order.desc("advert.created_at"));
			}
			else{
				Iterator<Order> i = pageable.getSort().iterator();
				while(i.hasNext()){
					Order o = i.next();
					if(o.getProperty().equals("advertName")){
						if(o.isAscending()){
							cr.addOrder(org.hibernate.criterion.Order.asc("advert.advertName"));
						}
						else{
							cr.addOrder(org.hibernate.criterion.Order.desc("advert.advertName"));
						}
					}
					else if(o.getProperty().equals("cost")){
						if(o.isAscending()){
							cr.addOrder(org.hibernate.criterion.Order.asc("advert.cost"));
						}
						else{
							cr.addOrder(org.hibernate.criterion.Order.desc("advert.cost"));
						}
					}
					else if(o.getProperty().equals("date")){
						if(o.isAscending()){
							cr.addOrder(org.hibernate.criterion.Order.asc("advert.created_at"));
						}
						else{
							cr.addOrder(org.hibernate.criterion.Order.desc("advert.created_at"));
						}
					}
				}
			}
		
			List uniqueSubList = cr.list();
		
			cr.setProjection(null);
			cr.setFirstResult(0); cr.setMaxResults(Integer.MAX_VALUE);
			cr.add(Restrictions.in("advert.id", uniqueSubList));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<Advert> searchResults = cr.list();
		
			Page<Advert> page = new PageImpl<Advert>(searchResults, pageable, totalResultCount);
		
			return page;
		}
	}
	
	@Override
	public List<Advert> allAdverts(){
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Advert");
		List<Advert> advertList = query.list();
		return advertList;
	}

	@Override
	public Page<Advert> searchAdverts(Map<String, Object> map, List<TechnicalEquipment> tech, Pageable pageable) {
		Session session = this.sessionFactory.getCurrentSession();
		
		Criteria cr = session.createCriteria(Advert.class, "advert");
		Projection idCountProjection = Projections.countDistinct("advert.id");
		
		cr.setProjection(idCountProjection);
		cr.add(Restrictions.eq("advert.isDeleted", Boolean.FALSE));
		String s = (String)map.get("advertName");
		if(s!=null && !s.trim().isEmpty()){
			cr.add(Restrictions.like("advert.advertName", s.trim(), MatchMode.ANYWHERE));
		}
		Boolean b = (Boolean)map.get("rent");
		Boolean bb = (Boolean)map.get("sale");
		if(!(b && bb)){
			if(b){
				cr.add(Restrictions.eq("rent_sale", b));
			}
			else if(bb){
				cr.add(Restrictions.eq("rent_sale", Boolean.FALSE));
			}
			else{
				cr.add(Restrictions.and(Restrictions.eq("advert.rent_sale", Boolean.TRUE), Restrictions.eq("advert.rent_sale", Boolean.FALSE)));
			}
		}
		cr.createAlias("advert.realestate", "realestate");
		
		if(!tech.isEmpty()){
			for (TechnicalEquipment tt : tech) {               
			    cr.add(Restrictions.sqlRestriction("? = some(select technical_equipment_id" +  " from " +
			                    "realestate_technical_equipment" + "  where {alias}.id" +
			                     " = " + "realestate_id" + ")",
			                    tt.getId(), StandardBasicTypes.INTEGER));
			}
		}
		
		s = (String)map.get("realestateName");
		if(s!=null && !s.trim().isEmpty()){
			cr.add(Restrictions.like("realestate.realestateName", s.trim(), MatchMode.ANYWHERE));
		}
		if(map.get("heating")!=null){
			b = (Boolean)map.get("heating");
			cr.add(Restrictions.eq("realestate.heating", b));
		}
		s = (String)map.get("category");
		if(s!=null && !s.trim().isEmpty()){
			cr.createAlias("realestate.category", "category");
			cr.add(Restrictions.eq("category.categoryName", s.trim()));
		}
		s = (String)map.get("type");
		if(s!=null && !s.trim().isEmpty()){
			cr.createAlias("realestate.type", "type");
			cr.add(Restrictions.eq("type.typeName", s.trim()));
		}
		Double d = (Double)map.get("priceFrom");
		if(d!=null){
			cr.add(Restrictions.ge("advert.cost", d));
		}
		d = (Double)map.get("priceTo");
		if(d!=null){
			cr.add(Restrictions.le("advert.cost", d));
		}
		d = (Double)map.get("areaFrom");
		if(d!=null){
			cr.add(Restrictions.ge("realestate.area", d));
		}
		d = (Double)map.get("areaTo");
		if(d!=null){
			cr.add(Restrictions.le("realestate.area", d));
		}
		if(map.get("street")!=null || map.get("streetNumber")!=null || map.get("region")!=null || map.get("city")!=null || map.get("postalCode")!=null){
			cr.createAlias("realestate.location", "location");
			s = (String)map.get("street");
			if(s!=null && !s.trim().isEmpty()){
				cr.add(Restrictions.like("location.street", s.trim(), MatchMode.ANYWHERE));
			}
			s = (String)map.get("region");
			if(s!=null && !s.trim().isEmpty()){
				cr.add(Restrictions.like("location.region", s.trim(), MatchMode.ANYWHERE));
			}
			s = (String)map.get("city");
			if(s!=null && !s.trim().isEmpty()){
				cr.add(Restrictions.like("location.city", s.trim(), MatchMode.ANYWHERE));
			}
			Integer i = (Integer)map.get("streetNumber");
			if(i!=null){
				cr.add(Restrictions.eq("location.streetNumber", i));
			}
			i = (Integer)map.get("postalCode");
			if(i!=null){
				cr.add(Restrictions.eq("location.postalCode", i));
			}
		}
		
		int totalResultCount = ((Long)cr.uniqueResult()).intValue();
		if(totalResultCount==0){
			pageable = new PageRequest(0, 1, null);
			Page<Advert> page = new PageImpl<Advert>(new ArrayList<Advert>(), pageable, totalResultCount);
			return page;
		}
		else{
			cr.setProjection(Projections.distinct(Projections.property("advert.id")));
		
			if(map.get("size")==null){
				cr.setMaxResults(12);
				cr.setFirstResult(pageable.getOffset());
				pageable = new PageRequest(pageable.getPageNumber(), 12, pageable.getSort());
			}
			else{
				cr.setMaxResults(pageable.getPageSize());
				cr.setFirstResult(pageable.getOffset());
			}
			if(pageable.getSort()==null){
				cr.addOrder(org.hibernate.criterion.Order.desc("advert.created_at"));
			}
			else{
				Iterator<Order> i = pageable.getSort().iterator();
				while(i.hasNext()){
					Order o = i.next();
					if(o.getProperty().equals("advertName")){
						if(o.isAscending()){
							cr.addOrder(org.hibernate.criterion.Order.asc("advert.advertName"));
						}
						else{
							cr.addOrder(org.hibernate.criterion.Order.desc("advert.advertName"));
						}
					}
					else if(o.getProperty().equals("cost")){
						if(o.isAscending()){
							cr.addOrder(org.hibernate.criterion.Order.asc("advert.cost"));
						}
						else{
							cr.addOrder(org.hibernate.criterion.Order.desc("advert.cost"));
						}
					}
					else if(o.getProperty().equals("date")){
						if(o.isAscending()){
							cr.addOrder(org.hibernate.criterion.Order.asc("advert.created_at"));
						}
						else{
							cr.addOrder(org.hibernate.criterion.Order.desc("advert.created_at"));
						}
					}
				}
			}
			List uniqueSubList = cr.list();
		
			cr.setProjection(null);
			cr.setFirstResult(0); cr.setMaxResults(Integer.MAX_VALUE);
			cr.add(Restrictions.in("advert.id", uniqueSubList));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<Advert> searchResults = cr.list();
		
			Page<Advert> page = new PageImpl<Advert>(searchResults, pageable, totalResultCount);
			return page;
		}
	}

	@Override
	public SoldAdvert addSoldAdvert(SoldAdvert s) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(s);
		return s;
	}

	

}
