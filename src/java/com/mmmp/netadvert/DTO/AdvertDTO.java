package com.mmmp.netadvert.DTO;

import java.util.Date;

public class AdvertDTO {
	
	private int advertId;
	
	private UserDTO advertUser;
	
	private Date created_at;
	
	private Date updated_at;
	
	private Date expire_date;
	
	private double advert_rate;
	
	private String advertName;
	
	private String contact;
	
	private String description;
	
	private Boolean is_sold;
	
	private Boolean is_deleted;
	
	private Boolean rent_sale;
	
	private double cost;
	
	private RealestateDTO realestate;
	
	public AdvertDTO(){
		
	}

	public int getAdvertId() {
		return advertId;
	}

	public void setAdvertId(int advertId) {
		this.advertId = advertId;
	}

	public UserDTO getAdvertUser() {
		return advertUser;
	}

	public void setAdvertUser(UserDTO advertUser) {
		this.advertUser = advertUser;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public Date getExpire_date() {
		return expire_date;
	}

	public void setExpire_date(Date expire_date) {
		this.expire_date = expire_date;
	}

	public double getAdvert_rate() {
		return advert_rate;
	}

	public void setAdvert_rate(double advert_rate) {
		this.advert_rate = advert_rate;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIs_sold() {
		return is_sold;
	}

	public void setIs_sold(Boolean is_sold) {
		this.is_sold = is_sold;
	}

	public Boolean getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public Boolean getRent_sale() {
		return rent_sale;
	}

	public void setRent_sale(Boolean rent_sale) {
		this.rent_sale = rent_sale;
	}

	public RealestateDTO getRealestate() {
		return realestate;
	}

	public void setRealestate(RealestateDTO realestate) {
		this.realestate = realestate;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getAdvertName() {
		return advertName;
	}

	public void setAdvertName(String advertName) {
		this.advertName = advertName;
	}
	
	
	
}
