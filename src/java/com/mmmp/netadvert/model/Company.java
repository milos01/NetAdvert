package com.mmmp.netadvert.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Set;

@Entity
@Table(name="Company")
public class Company {

	@Id
	@Column(name="Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique=true)
	private String company_name;

	@OneToOne(targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(nullable=false, name = "master_user_id")
	private User user;

	@JsonBackReference
	@OneToMany(mappedBy="company", fetch=FetchType.EAGER)
	private Set<CompanyStaffs> cs;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public Set<CompanyStaffs> getCs() {
		return cs;
	}

	public void setCs(Set<CompanyStaffs> cs) {
		this.cs = cs;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Company{" +
				"id=" + id +
				", company_name='" + company_name + '\'' +
				", user=" + user +
				", cs=" + cs +
				'}';
	}
}
