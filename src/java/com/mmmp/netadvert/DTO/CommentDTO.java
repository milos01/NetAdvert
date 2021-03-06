package com.mmmp.netadvert.DTO;

import java.util.Date;

public class CommentDTO {

	private int id;
	
	private UserDTO user;
	
	private AdvertDTO advert;

	private Date date;
	
	private String text;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public AdvertDTO getAdvert() {
		return advert;
	}

	public void setAdvert(AdvertDTO advert) {
		this.advert = advert;
	}
	
}
