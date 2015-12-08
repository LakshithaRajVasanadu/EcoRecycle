package com.ecoRecycle.service;

import java.util.List;

import com.ecoRecycle.model.Location;
import com.ecoRecycle.repository.LocationRepository;

public class LocationService
{
	
	LocationRepository repository = new LocationRepository();
	
	/*To get all the locations*/
	public List<Location> getAllLocations() {
		List<Location> locations = repository.getAllLocations();
		return locations;
	}
	
	/*To get a location by id*/
	public Location getLocationById(int id) {
		Location location = repository.getLocationById(id);
		return location;
	}
	
	/*To get location by city*/
	public Location getLocationByCity(String city) {
		Location location = repository.getLocationByCity(city);
		return location;
	}
}
