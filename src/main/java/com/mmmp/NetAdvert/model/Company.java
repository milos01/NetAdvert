package com.mmmp.NetAdvert.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Company")
public class Company {

	@Id
	@Column(name="Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String company_name;
	
	@OneToOne(targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(nullable=false, name = "master_user_id")
	private User companyAdmin;

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

	public User getCompanyAdmin() {
		return companyAdmin;
	}

	public void setCompanyAdmin(User companyAdmin) {
		this.companyAdmin = companyAdmin;
	}
	
	
}
