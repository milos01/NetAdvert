package com.mmmp.netadvert.DAO;

import com.mmmp.netadvert.model.AdvertRating;


public interface AdvertRatingDAO {
	
	public AdvertRating addAdvertRating(AdvertRating a);
	
	public AdvertRating updateAdvertRating(AdvertRating a);
	
	public void deleteAdvertRating(AdvertRating a);
	
	public AdvertRating getUserOfAdvertRaiting(int user_id,int advert_id);

}
