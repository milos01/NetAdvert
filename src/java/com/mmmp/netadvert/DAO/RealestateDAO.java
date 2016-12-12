package com.mmmp.netadvert.DAO;

import java.util.List;

import com.mmmp.netadvert.model.Realestate;
import com.mmmp.netadvert.model.RealestateCategory;
import com.mmmp.netadvert.model.RealestateType;
import com.mmmp.netadvert.model.TechnicalEquipment;

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
