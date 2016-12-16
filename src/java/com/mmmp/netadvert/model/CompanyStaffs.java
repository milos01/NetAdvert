package com.mmmp.netadvert.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="company_staffs")
public class CompanyStaffs {

	@Id
	@Column(name="Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(nullable=false, name = "company_id")
	private Company company;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(nullable=false, name = "user_id")
	private User user;
	
	private int accepted;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getAccepted() {
		return accepted;
	}

	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}

	
}
