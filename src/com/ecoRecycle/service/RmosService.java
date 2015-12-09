package com.ecoRecycle.service;

import java.util.HashSet;
import java.util.List;
import java.util.*;



import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.repository.RmosRepository;

public class RmosService {
	
	RmosRepository repository = new RmosRepository();
	
	/*To get all rmos*/
	public Set<Rmos> getAllRmos() {
		List<Rmos> rmosList = repository.getAllRmos();
		Set<Rmos> rmosUniqueList = new HashSet<Rmos>();
		for(Rmos rmos: rmosList)
			rmosUniqueList.add(rmos);
			
		return rmosUniqueList;
	}
	
	/*To get rmos by name*/
	public Rmos getRmosByName(String name) {
		Rmos rmos = repository.getRmosByName(name);
		return rmos;
	}

}
