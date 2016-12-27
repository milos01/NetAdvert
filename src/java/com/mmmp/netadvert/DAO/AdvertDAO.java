package com.mmmp.netadvert.DAO;

import com.mmmp.netadvert.DTO.SearchDTO;
import com.mmmp.netadvert.model.Advert;
import com.mmmp.netadvert.model.Picture;
import com.mmmp.netadvert.model.TechnicalEquipment;

import java.util.List;


public interface AdvertDAO {

	
	public Advert findAdvert(int id);
	
	public Advert addAdvert(Advert a);
	
	public Advert updateAdvert(Advert a);
	
	public boolean deleteAdvert(Advert a);
	
	public List<Advert> findAdvertByName(String name);
	
	public List<Advert> allAdverts();
	
	public List<Advert> searchAdverts(SearchDTO search, List<TechnicalEquipment> tech);

}
