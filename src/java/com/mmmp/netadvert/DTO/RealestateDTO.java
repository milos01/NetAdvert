package com.mmmp.netadvert.DTO;

import java.util.List;

public class RealestateDTO {
	
	private int realestateId;
	
	private String realestateName;
	
	private RealestateTypeDTO type;

    private double area;
    
    private LocationDTO location;

    private boolean heating;
    
    private RealestateCategoryDTO category;
    
    private List<Boolean> equipments;
    
    public RealestateDTO(){
    	
    }

	public int getRealestateId() {
		return realestateId;
	}

	public void setRealestateId(int realestateId) {
		this.realestateId = realestateId;
	}

	public String getRealestateName() {
		return realestateName;
	}

	public void setRealestateName(String realestateName) {
		this.realestateName = realestateName;
	}

	public RealestateTypeDTO getType() {
		return type;
	}

	public void setType(RealestateTypeDTO type) {
		this.type = type;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	public boolean isHeating() {
		return heating;
	}

	public void setHeating(boolean heating) {
		this.heating = heating;
	}

	public RealestateCategoryDTO getCategory() {
		return category;
	}

	public void setCategory(RealestateCategoryDTO category) {
		this.category = category;
	}

	public List<Boolean> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<Boolean> equipments) {
		this.equipments = equipments;
	}
    
    

}
