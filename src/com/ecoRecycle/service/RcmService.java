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
	
	/*
	 * public Message addItemToTransaction(String itemType, double weight, Rcm rcm) {
	 * 
	 *  // Check item weight based on type
	 *  if not valid, return error Message object
	 *  and also check with rcm's capacity
	 *  
	 * 	// Get last transaction for that rcm -
	 *  //  Add item (transaction Service)
	 *  
	 *  // If add item is successful,
	 *  // Update trans - total weight, total payment
	 *  
	 *  // Update rcm values
	 *  // Update current capacity of rcm
	 *  
	 *  // Try doing evrything in one session - save rcm
	 * }
	 * 
	 */
	
	
	/*
	 * HelperModel Dispense (Rcm rcm) {
	 *  //get last transaction for rcm status = ACTIVE
	 *  // get total amount
	 *  // check if cash or coupon
	 *  // send a obj back
	 */
	
}
