package com.mmmp.NetAdvert.DAO;

import com.mmmp.NetAdvert.model.CompanyStaffs;

public interface CompanyDAO {

	public CompanyStaffs getUserOfCompany(int user_id,int company_id);
	
	public void updateCompanyStaff(CompanyStaffs cs);
}
