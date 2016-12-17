package com.mmmp.netadvert.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.beans.factory.annotation.Required;

@Entity
@Table(name="User")

public class User {

	@Id
	@Column(name="Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String first_name;
	
	private String last_name;

	private String email;

	@NotNull
	private String password;
	
	@OneToOne(targetEntity = Role.class,fetch=FetchType.EAGER)
	@JoinColumn(nullable=false, name = "role_id")
	private Role role;

	private int user_rate;
	
	@JsonBackReference
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private Set<Picture> pictures;

	@JsonBackReference
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private Set<Advert> adverts;
	
	@JsonBackReference
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private Set<Report> reports;

    @JsonBackReference
    @OneToMany(mappedBy="user", fetch=FetchType.EAGER)
    private Set<CompanyStaffs> cs;
    
    @JsonBackReference
    @OneToMany(mappedBy="user", fetch=FetchType.EAGER)
    private Set<AdvertRating> advertRatings;

    public Set<CompanyStaffs> getCs() {
        return cs;
    }

    public void setCs(Set<CompanyStaffs> cs) {
        this.cs = cs;
    }

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getUser_rate() {
		return user_rate;
	}

	public void setUser_rate(int user_rate) {
		this.user_rate = user_rate;
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
	
	
	public Set<Report> getReports() {
		return reports;
	}

	public void setReports(Set<Report> reports) {
		this.reports = reports;
	}
	
	

	public Set<AdvertRating> getAdvertRatings() {
		return advertRatings;
	}

	public void setAdvertRatings(Set<AdvertRating> advertRatings) {
		this.advertRatings = advertRatings;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", first_name='" + first_name + '\'' +
				", last_name='" + last_name + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", role=" + role +
				", user_rate=" + user_rate +
				", pictures=" + pictures +
				", adverts=" + adverts +
				", reports=" + reports +
				'}';
	}
}
