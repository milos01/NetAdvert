package com.mmmp.NetAdvert.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
public class Realestate {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="realestate_name")
	private String realestateName;
	
	@JsonManagedReference
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="type_id", nullable=false)
	private RealestateType type;
	
	private double cost;
	
	private double area;
	
	@JsonManagedReference
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="location_id", nullable=false)
	private Location location;
	
	private boolean heating;
	
	@JsonManagedReference
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="category_id", nullable=false)
	private RealestateCategory category;
	
	@JsonManagedReference
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name="realestate_technical_equipment", joinColumns=@JoinColumn(name="realestate_id"), inverseJoinColumns=@JoinColumn(name="technical_equipment_id"))
	private Set<TechnicalEquipment> technicalEquipments;
	
	@JsonBackReference
	@OneToMany(mappedBy="realestate", fetch=FetchType.EAGER)
	private Set<Picture> pictures;
	
	@JsonBackReference
	@OneToMany(mappedBy="realestate", fetch=FetchType.LAZY)
	private Set<Advert> adverts;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRealestateName() {
		return realestateName;
	}

	public void setRealestateName(String realestateName) {
		this.realestateName = realestateName;
	}

	

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	

	public boolean isHeating() {
		return heating;
	}

	public void setHeating(boolean heating) {
		this.heating = heating;
	}

	public RealestateType getType() {
		return type;
	}

	public void setType(RealestateType type) {
		this.type = type;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public RealestateCategory getCategory() {
		return category;
	}

	public void setCategory(RealestateCategory category) {
		this.category = category;
	}

	public Set<TechnicalEquipment> getTechnicalEquipments() {
		return technicalEquipments;
	}

	public void setTechnicalEquipments(Set<TechnicalEquipment> technicalEquipments) {
		this.technicalEquipments = technicalEquipments;
	}

	public Set<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}

	public Set<Advert> getAdverts() {
		return adverts;
	}

	public void setAdverts(Set<Advert> adverts) {
		this.adverts = adverts;
	}

	
	
}
