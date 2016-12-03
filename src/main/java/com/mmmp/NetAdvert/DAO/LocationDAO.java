package com.mmmp.NetAdvert.DAO;

import com.mmmp.NetAdvert.model.Location;

public interface LocationDAO {

	public void createLocation(Location location);

	public Location findLocation(int id);
	
	public void updateLocation(Location location);
	
	public Location checkForExistingLocation(String street, int street_number, String region, String city, int postal_code);
}
