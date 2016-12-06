package com.mmmp.NetAdvert.DAO;

import java.util.List;

import com.mmmp.NetAdvert.model.Realestate;
import com.mmmp.NetAdvert.model.RealestateCategory;
import com.mmmp.NetAdvert.model.RealestateType;
import com.mmmp.NetAdvert.model.TechnicalEquipment;

public interface RealestateDAO {
	
	public boolean checkExistingCategoryType(int category_id, int type_id);
	
	public RealestateCategory findRealestateCategory(int category_id);
	
	public Realestate addRealestate(Realestate rs);
	
	public Realestate updateRealestate(Realestate rs);
	
	public Realestate findRealestate(int id);
	
	public List<RealestateType> findAllRealstateTypes();
	
	public RealestateType findRealestateType(String name);
	
	public RealestateType findRealestateTypeById(int id);
	
	public List<RealestateCategory> findAllRealestateCategory();
	
	public RealestateCategory findRealestateCategory(String name);
	
	public List<TechnicalEquipment> allEquipment();
	
	public TechnicalEquipment findTechnicalEquipmentById(int id);

}
