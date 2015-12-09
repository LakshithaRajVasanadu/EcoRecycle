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

public class StatusManager extends Observable{
	private Rmos rmos;
	RmosRepository repository;
	
	
	public StatusManager(Rmos rmos) {
		this.rmos = rmos;
		this.repository = new RmosRepository();
	}
	
	/*To activate an inactive rcm*/
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
		} else {
			this.rmos = repository.getRmosById(this.rmos.getId());
			
		}
		setChanged();
		notifyObservers();
		
		return msg;
	}
	
		
	/*To return a message when the rcm is deactivated and to set the sattsu of the rcm as inactive*/
	public Message deactivateRcm(int id, String reason) {
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
		rcm.setReason(reason);
		
		msg.setSuccessful(rcmService.updateRcm(rcm));
		if(!msg.isSuccessful()) {
			msg.setMessage("Could not deactivate Rcm");
		} else {
			this.rmos = repository.getRmosById(this.rmos.getId());
			
		}
		setChanged();
		notifyObservers();
		
		return msg;
	}
}
