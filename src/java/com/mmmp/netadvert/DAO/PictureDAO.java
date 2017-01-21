package com.mmmp.netadvert.DAO;

import com.mmmp.netadvert.model.Picture;

import java.util.List;

public interface PictureDAO {
	
	public void addPicture(Picture i);
	
	public void deletePicture(Picture i);
	
	public Picture findPicture(int id);
	
	public Picture findPictureByName(String name);

	public Picture getAdvertMainPicture(int id);

    public List<Picture> getAdvertPictures(int aid);
}
