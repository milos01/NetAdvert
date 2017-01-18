package com.mmmp.netadvert.DAO;

import com.mmmp.netadvert.model.UserRating;

public interface UserRatingDAO {
	
	public UserRating addUserRating(UserRating u);
	
	public UserRating updateUserRating(UserRating ur);
	
	public void deleteUserRating(UserRating ur);

	public UserRating getUserOfUserRaiting(int user_id,int user_idP);
}
