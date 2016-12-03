package com.mmmp.NetAdvert.DAO;

import org.springframework.stereotype.Repository;

import com.mmmp.NetAdvert.model.Advert;

@Repository
public interface AdvertDAO {

	
	public Advert findAdvert(int id);
	
	public Advert addAdvert(Advert a);
}
