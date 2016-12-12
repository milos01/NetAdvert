package com.mmmp.netadvert.DAO;

import com.mmmp.netadvert.model.Advert;

import org.springframework.stereotype.Repository;

@Repository
public interface AdvertDAO {

	
	public Advert findAdvert(int id);
	
	public Advert addAdvert(Advert a);
}
