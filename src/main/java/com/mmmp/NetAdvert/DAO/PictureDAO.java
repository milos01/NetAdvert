package com.mmmp.NetAdvert.DAO;

import com.mmmp.NetAdvert.model.Picture;

public interface PictureDAO {
	
	public void addPicture(Picture i);
	
	public void deletePicture(Picture i);
	
	public Picture findPicture(int id);
	
	

}
