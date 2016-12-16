package com.mmmp.netadvert.DAO;

import com.mmmp.netadvert.model.Advert;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface AdvertDAO {

	
	public Advert findAdvert(int id);
	
	public Advert addAdvert(Advert a);
	
	public Advert updateAdvert(Advert a);
	
	public boolean deleteAdvert(Advert a);
	
	public List<Advert> findAdvertByName(String name);
	
	public List<Advert> allAdverts();
}
