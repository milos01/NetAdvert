package com.mmmp.netadvert.DAO;

import com.mmmp.netadvert.DTO.SearchDTO;
import com.mmmp.netadvert.model.Advert;
import com.mmmp.netadvert.model.Picture;
import com.mmmp.netadvert.model.SoldAdvert;
import com.mmmp.netadvert.model.TechnicalEquipment;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AdvertDAO {

	
	public Advert findAdvert(int id);
	
	public Advert addAdvert(Advert a);
	
	public Advert updateAdvert(Advert a);
	
	public boolean deleteAdvert(Advert a);
	
	public List<Advert> findAdvertByName(String name);
	
	public Page<Advert> allAdvertsPage(Map<String, String> map, Pageable pageable);
	
	public List<Advert> allAdverts();
	
	public Page<Advert> searchAdverts(Map<String, Object> map, List<TechnicalEquipment> tech, Pageable pageable);
	
	public SoldAdvert addSoldAdvert(SoldAdvert s);
	
	public List<Advert> getAllAdvertsOfUser(int user_id);

}
