package com.ecoRecycle.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import com.ecoRecycle.helper.Message;
import com.ecoRecycle.helper.RcmStatus;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.model.RmosRcmMapping;
import com.ecoRecycle.repository.RmosRepository;

public class RmosManager extends Observable{
	
	private Rmos rmos;
	RmosRepository repository;
	
	
	public RmosManager(Rmos rmos) {
		this.rmos = rmos;
		this.repository = new RmosRepository();
	}
	
	/*To add a rcm to a particular rmos*/
	public Message addRcm(String name, String location, double capacity, double cashValue ) {
		LocationService locationService = new LocationService();
		RcmService rcmService = new RcmService();
		
		Message msg = new Message();
		msg.setSuccessful(true);
		
		Rcm rcm = rcmService.getRcmByName(name);
		if(rcm != null) {
			msg.setSuccessful(false);
			msg.setMessage("Rcm Already exists");
			return msg;
		}
		
		rcm = new Rcm();
		rcm.setName(name);
		rcm.setLocation(locationService.getLocationByCity(location));
		rcm.setTotalCapacity(capacity);
		rcm.setTotalCashValue(cashValue);
		rcm.setStatus(RcmStatus.ACTIVE);
		rcm.setReason("Admin Change");
		
		RmosRcmMapping mapping = new RmosRcmMapping();
		mapping.setRcm(rcm);
		mapping.setRmos(this.rmos);
		mapping.setValid(true);

		this.rmos.addRmosRcmMapping(mapping);
				
		msg.setSuccessful(repository.updateRmos(this.rmos));
		if(!msg.isSuccessful()) 
			msg.setMessage("Internal Error");
		else {
			this.rmos = repository.getRmosById(this.rmos.getId());
			
		}
		setChanged();
		notifyObservers();
		
		return msg;
	}
	
	/*To remove an rcm under a particular rmos*/
	public Message removeRcm(int id) {
		RcmService rcmService = new RcmService();
		Rcm rcm ;
		
		Message msg = new Message();
		msg.setSuccessful(true);
		
		rcm = rcmService.getRcmById(id);
		if(rcm == null) {
			msg.setSuccessful(false);
			msg.setMessage("Rcm Does not exist");
			return msg;
		}
		
		RmosRcmMapping mapping = repository.getMappingForRcm(this.rmos, rcm); 
		if(mapping == null) {
			msg.setSuccessful(false);
			msg.setMessage("Rcm is Not Mapped to this Rmos");
			return msg;
		}

		mapping.setValid(false);
		mapping.getRcm().setStatus(RcmStatus.REMOVED);
		
		msg.setSuccessful(repository.updateRmos(this.rmos));
		if(!msg.isSuccessful()) 
			msg.setMessage("Internal Error");
		else {
			this.rmos = repository.getRmosById(this.rmos.getId());
			
		}
		setChanged();
		notifyObservers();
		
		return msg;
	}
	
	/*To get a rcm by id*/
	public Rcm getRcmById(int id) {
		RcmService rcmService = new RcmService();
		Rcm rcm = rcmService.getRcmById(id);
		return rcm;
	}
	
	/*To get all rcm's*/
	public List<Rcm> getAllRcms() {
		Set<RmosRcmMapping> mappings = this.rmos.getRmosRcmMappings();
		List<Rcm> rcms = new ArrayList<Rcm>();

		Iterator<RmosRcmMapping> iter = mappings.iterator();
		while (iter.hasNext()) {
			RmosRcmMapping mapping = iter.next();
			if(mapping.isValid())
				rcms.add(mapping.getRcm());
		}
		
		return rcms;
	}
		
}
