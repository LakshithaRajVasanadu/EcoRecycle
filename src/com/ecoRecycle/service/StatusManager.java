package com.ecoRecycle.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ecoRecycle.helper.Message;
import com.ecoRecycle.helper.RcmStatus;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.model.RmosRcmMapping;
import com.ecoRecycle.repository.RmosRepository;

public class StatusManager {
	private Rmos rmos;
	RmosRepository repository;
	
	
	public StatusManager(Rmos rmos) {
		this.rmos = rmos;
		this.repository = new RmosRepository();
	}
	
	public List<Rcm> getAllRcms() {
		Set<RmosRcmMapping> mappings = this.rmos.getRmosRcmMappings();
		List<Rcm> rcms = new ArrayList<Rcm>();

		Iterator<RmosRcmMapping> iter = mappings.iterator();
		while (iter.hasNext()) {
			RmosRcmMapping mapping = iter.next();
			rcms.add(mapping.getRcm());
		}
		
		return rcms;
	}
	
	public Message activateRcm(int id) {
		RcmService rcmService = new RcmService();
		Rcm rcm = rcmService.getRcmById(id);
		
		Message msg = new Message();
		msg.setSuccessful(true);
		
		if(rcm == null) {
			msg.setSuccessful(false);
			msg.setMessage("Could not find Rcm");
			return msg;
		}
		
		rcm.setStatus(RcmStatus.ACTIVE);
		
		msg.setSuccessful(rcmService.updateRcm(rcm));
		if(!msg.isSuccessful()) {
			msg.setMessage("Could not activate Rcm");
		} else
			this.rmos = repository.getRmosById(this.rmos.getId());
		
		return msg;
	}
	
	public Message deactivateRcm(int id) {
		RcmService rcmService = new RcmService();
		Rcm rcm = rcmService.getRcmById(id);
		
		Message msg = new Message();
		msg.setSuccessful(true);
		
		if(rcm == null) {
			msg.setSuccessful(false);
			msg.setMessage("Could not find Rcm");
			return msg;
		}
		
		rcm.setStatus(RcmStatus.INACTIVE);
		
		msg.setSuccessful(rcmService.updateRcm(rcm));
		if(!msg.isSuccessful()) {
			msg.setMessage("Could not deactivate Rcm");
		} else
			this.rmos = repository.getRmosById(this.rmos.getId());
		
		return msg;
	}
}
