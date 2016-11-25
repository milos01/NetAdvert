package com.mmmp.NetAdvert.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Advert")
public class Advert {

	@Id
	@Column(name="Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(nullable=false, name = "user_id")
	private User user;
	
	private Date created_at;
	
	private Date updated_at;
	
	private Date expire_date;
	
	private int advert_rate;
	
	private String contact;
	
	private String description;
	
	private Boolean is_sold;
	
	private Boolean is_deleted;
	
	private Boolean rent_sale;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="realestate_id", nullable=false)
	private Realestate realestate;
	
	@OneToMany(mappedBy="advert", fetch=FetchType.LAZY)
	private Set<Report> reports;

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

	public int getAdvert_rate() {
		return advert_rate;
	}

	public void setAdvert_rate(int advert_rate) {
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

	public Realestate getRealestate() {
		return realestate;
	}

	public void setRealestate(Realestate realestate) {
		this.realestate = realestate;
	}

	public Set<Report> getReports() {
		return reports;
	}

	public void setReports(Set<Report> reports) {
		this.reports = reports;
	}
	
	
	
}
