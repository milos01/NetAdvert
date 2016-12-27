package com.mmmp.netadvert.DAO;

import com.mmmp.netadvert.model.Picture;

public interface PictureDAO {
	
	public void addPicture(Picture i);
	
	public void deletePicture(Picture i);
	
	public Picture findPicture(int id);
	
	public Picture findPictureByName(String name);

	public Picture getAdvertMainPicture(int id);

}
