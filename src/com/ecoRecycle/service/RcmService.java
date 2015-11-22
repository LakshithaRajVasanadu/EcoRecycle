package com.ecoRecycle.service;

import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.repository.RcmRepository;

public class RcmService {
	
	private RcmRepository repository = new RcmRepository();
	
	public Rcm getRcmById(int id) {
		Rcm rcm = repository.getRcmById(id);
		return rcm;
	}
	
	public Rcm getRcmByName(String name) {
		Rcm rcm = repository.getRcmByName(name);
		return rcm;
	}
	
	public boolean updateRcm(Rcm rcm) {
		return repository.updateRcm(rcm);
	}
	
}
