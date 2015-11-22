package com.ecoRecycle.service;

import java.util.List;

import com.ecoRecycle.model.Location;
import com.ecoRecycle.repository.LocationRepository;

public class LocationService
{
	
	LocationRepository repository = new LocationRepository();
	
	public List<Location> getAllLocations() {
		List<Location> locations = repository.getAllLocations();
		return locations;
	}
	
	public Location getLocationById(int id) {
		Location location = repository.getLocationById(id);
		return location;
	}
	
	public Location getLocationByCity(String city) {
		Location location = repository.getLocationByCity(city);
		return location;
	}
}
