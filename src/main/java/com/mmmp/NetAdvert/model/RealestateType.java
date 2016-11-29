package com.mmmp.NetAdvert.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="realestate_type")
public class RealestateType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="type_name")
	private String typeName;
	
	@JsonBackReference
	@ManyToMany(mappedBy="types", fetch=FetchType.LAZY)
	private Set<RealestateCategory> categories;
	
	@JsonBackReference
	@OneToMany(mappedBy="type", fetch=FetchType.LAZY)
	private Set<Realestate> realestates;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
