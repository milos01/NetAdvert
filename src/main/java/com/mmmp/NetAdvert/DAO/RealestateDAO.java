package com.mmmp.NetAdvert.DAO;

import com.mmmp.NetAdvert.model.Realestate;
import com.mmmp.NetAdvert.model.RealestateCategory;

public interface RealestateDAO {
	
	public boolean checkExistingCategoryType(int category_id, int type_id);
	
	public RealestateCategory findRealestateCategory(int category_id);
	
	public Realestate addRealestate(Realestate rs);
	
	public Realestate updateRealestate(Realestate rs);
	
	public Realestate findRealestate(int id);

}
