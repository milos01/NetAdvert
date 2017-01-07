package com.mmmp.netadvert.DAO;

import com.mmmp.netadvert.model.Company;
import com.mmmp.netadvert.model.CompanyStaffs;

import java.util.List;

public interface CompanyDAO {

	public CompanyStaffs getUserOfCompany(int user_id, int company_id);
	
	public void updateCompanyStaff(CompanyStaffs cs);

	public Company addCompany(Company company);

	public Company findCompany(int cid);

	public void addCompanyStaff(CompanyStaffs cs);

	public List<CompanyStaffs> allCompanyStaff();

	public List<Company> getAllCompanys();

	public List<CompanyStaffs> staffForCompany(int cid);
}
