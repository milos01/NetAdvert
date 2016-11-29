package com.mmmp.NetAdvert.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="technical_equipment")
public class TechnicalEquipment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="equipment_name")
	private String equipmentName;
	
	@JsonBackReference
	@ManyToMany(mappedBy="equipments", fetch=FetchType.LAZY)
	private Set<RealestateCategory> categories;
	
	@JsonBackReference
	@ManyToMany(mappedBy="technicalEquipments", fetch=FetchType.LAZY)
	private Set<Realestate> realestates;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public Set<RealestateCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<RealestateCategory> categories) {
		this.categories = categories;
	}

	public Set<Realestate> getRealestates() {
		return realestates;
	}

	public void setRealestates(Set<Realestate> realestates) {
		this.realestates = realestates;
	}

}
