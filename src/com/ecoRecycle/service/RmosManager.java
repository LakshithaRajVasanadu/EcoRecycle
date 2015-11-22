package com.ecoRecycle.service;

import com.ecoRecycle.helper.Message;
import com.ecoRecycle.helper.RcmStatus;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.model.RmosRcmMapping;
import com.ecoRecycle.repository.RmosRepository;

public class RmosManager {
	
	private Rmos rmos;
	RmosRepository repository;
	
	
	public RmosManager(Rmos rmos) {
		this.rmos = rmos;
		this.repository = new RmosRepository();
	}
	
	
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
		
		RmosRcmMapping mapping = new RmosRcmMapping();
		mapping.setRcm(rcm);
		mapping.setRmos(this.rmos);
		mapping.setValid(true);

		this.rmos.addRmosRcmMapping(mapping);
				
		msg.setSuccessful(repository.updateRmos(this.rmos));
		if(!msg.isSuccessful()) 
			msg.setMessage("Internal Error");
		else
			this.rmos = repository.getRmosById(this.rmos.getId());
		
		return msg;
	}
	
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
		System.out.println("Mapping.." + rcm.getId());
		
		RmosRcmMapping mapping = repository.getMappingForRcm(this.rmos, rcm); 
		if(mapping == null) {
			msg.setSuccessful(false);
			msg.setMessage("Rcm is Not Mapped to this Rmos");
			return msg;
		}

		mapping.setValid(false);
		mapping.getRcm().setStatus(RcmStatus.INACTIVE);
		
		msg.setSuccessful(repository.updateRmos(this.rmos));
		if(!msg.isSuccessful()) 
			msg.setMessage("Internal Error");
		else
			this.rmos = repository.getRmosById(this.rmos.getId());
		
		return msg;
	}
	
	public Rcm getRcmById(int id) {
		RcmService rcmService = new RcmService();
		Rcm rcm = rcmService.getRcmById(id);
		return rcm;
	}
		
}
