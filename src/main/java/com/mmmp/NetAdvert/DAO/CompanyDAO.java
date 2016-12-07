package com.mmmp.NetAdvert.DAO;

import com.mmmp.NetAdvert.model.Company;
import com.mmmp.NetAdvert.model.CompanyStaffs;
import com.mmmp.NetAdvert.model.User;

public interface CompanyDAO {

	public CompanyStaffs getUserOfCompany(int user_id,int company_id);
	
	public void updateCompanyStaff(CompanyStaffs cs);

	public Company addCompany(Company company);
}
