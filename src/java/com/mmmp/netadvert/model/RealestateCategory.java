package com.mmmp.netadvert.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="realestate_category")
public class RealestateCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="category_name")
	private String categoryName;
	
	@JsonBackReference
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="realestate_category_technical_equipment", joinColumns=@JoinColumn(name="realestate_category_id"), inverseJoinColumns=@JoinColumn(name="technical_equipment_id"))
	private Set<TechnicalEquipment> equipments;
	
	@JsonManagedReference
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="realestate_category_realestate_type", joinColumns=@JoinColumn(name="realestate_category_id"), inverseJoinColumns=@JoinColumn(name="realestate_type_id"))
	private Set<RealestateType> types;
	
	@JsonBackReference
	@OneToMany(mappedBy="category", fetch=FetchType.LAZY)
	private Set<Realestate> realestates;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Set<TechnicalEquipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(Set<TechnicalEquipment> equipments) {
		this.equipments = equipments;
	}

	public Set<RealestateType> getTypes() {
		return types;
	}

	public void setTypes(Set<RealestateType> types) {
		this.types = types;
	}

	public Set<Realestate> getRealestates() {
		return realestates;
	}

	public void setRealestates(Set<Realestate> realestates) {
		this.realestates = realestates;
	}

	

}
