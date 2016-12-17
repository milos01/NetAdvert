package com.mmmp.netadvert.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="advert_rating")
public class AdvertRating {
	
	@Id
	@Column(name="Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JsonManagedReference
	@ManyToOne(targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(nullable=false, name = "user_id")
	private User user;
	
	@JsonManagedReference
	@ManyToOne(targetEntity = Advert.class,fetch=FetchType.EAGER)
	@JoinColumn(nullable=false, name = "advert_id")
	private Advert advert;
	
	private int rating;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Advert getAdvert() {
		return advert;
	}

	public void setAdvert(Advert advert) {
		this.advert = advert;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	
	
}
