package com.mmmp.netadvert.DTO;

public class LocationDTO {

	    private int locationId;

	    private String street;

	    private int streetNumber;

	    private String region;

	    private String city;

	    private int postalCode;
	    
	    public LocationDTO(){
	    	
	    }
	    
		public int getLocationId() {
			return locationId;
		}

		public void setLocationId(int locationId) {
			this.locationId = locationId;
		}

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public int getStreetNumber() {
			return streetNumber;
		}

		public void setStreetNumber(int streetNumber) {
			this.streetNumber = streetNumber;
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public int getPostalCode() {
			return postalCode;
		}

		public void setPostalCode(int postalCode) {
			this.postalCode = postalCode;
		}
	    
	    
}
