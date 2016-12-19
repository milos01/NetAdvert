package com.mmmp.netadvert.DTO;

import java.util.List;

public class SearchDTO {
	
	private Boolean rent_sale;
	
	private String type_name;
	
	private String category_name;
	
	private String city;
	
	private String region;
	
	private String street;
	
	private int postal_code;
	
	private double price_from;
	
	private double price_to;
	
	private double area_from;
	
	private double area_to;
	
	private Boolean heating;
	
	private List<String> equipments;
	
	public SearchDTO(){}

	public SearchDTO(Boolean rent_sale, String type_name, String category_name, String city, String region,
			String street, int postal_code, double price_from, double price_to, double area_from, double area_to,
			Boolean heating, List<String> equipments) {
		super();
		this.rent_sale = rent_sale;
		this.type_name = type_name;
		this.category_name = category_name;
		this.city = city;
		this.region = region;
		this.street = street;
		this.postal_code = postal_code;
		this.price_from = price_from;
		this.price_to = price_to;
		this.area_from = area_from;
		this.area_to = area_to;
		this.heating = heating;
		this.equipments = equipments;
	}

	public Boolean getRent_sale() {
		return rent_sale;
	}

	public void setRent_sale(Boolean rent_sale) {
		this.rent_sale = rent_sale;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(int postal_code) {
		this.postal_code = postal_code;
	}

	public double getPrice_from() {
		return price_from;
	}

	public void setPrice_from(double price_from) {
		this.price_from = price_from;
	}

	public double getPrice_to() {
		return price_to;
	}

	public void setPrice_to(double price_to) {
		this.price_to = price_to;
	}

	public double getArea_from() {
		return area_from;
	}

	public void setArea_from(double area_from) {
		this.area_from = area_from;
	}

	public double getArea_to() {
		return area_to;
	}

	public void setArea_to(double area_to) {
		this.area_to = area_to;
	}

	public Boolean getHeating() {
		return heating;
	}

	public void setHeating(Boolean heating) {
		this.heating = heating;
	}

	public List<String> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<String> equipments) {
		this.equipments = equipments;
	}
	
	
	
}
