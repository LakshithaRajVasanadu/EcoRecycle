package com.ecoRecycle.service;


import java.util.Date;
import java.util.Set;

import com.ecoRecycle.helper.TransactionStatus;
import com.ecoRecycle.helper.TransactionType;
import com.ecoRecycle.model.Item;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Transaction;
import com.ecoRecycle.repository.TransactionRepository;

import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Transaction;


public class TransactionService {
	

	// public Transaction getLastTransaction(Rcm rcm) {
	//		rcm.getTransactions
//			Loop over this to get the transaction with max id
//			check that transaction status
//			obj trans
//			if(status == DONE)
//				trans = create a new transaction
//			else
//				trans = last obtained trans
//				
//		    return trans;
//					
	//}
	
	public Transaction getLastTransaction(Rcm rcm)
	{
		Transaction lastTransaction = null;
		int maxId = -1;
		Transaction mostRecentTransaction = null;
		Set<Transaction> transactions;
		int newId = -1;
		try{
			transactions = rcm.getTransactions();
			for(Transaction t : transactions) {
				if(t.getType().equals(TransactionType.RECYCLE)) {
					if(t.getId() > maxId) {
						mostRecentTransaction = t;
						maxId = t.getId();
					}
				}
			}
			
			//check type is recycle in if cond
			//if maxId == -1 (means no transaction exists for this rcm. So create a new transaction)
			if((maxId == -1) || 
			   (mostRecentTransaction.getStatus().equals(TransactionStatus.DONE))){
				lastTransaction = new Transaction();
				//newId = mostRecentTransaction.getId() + 1;
				//lastTransaction.setId(newId);
				lastTransaction.setRcm(rcm);
				lastTransaction.setTotalWeight(0);
				lastTransaction.setTotalPayment(0);
				lastTransaction.setType(TransactionType.RECYCLE);
				//lastTransaction.setId(++maxId);
				//lastTransaction.setCreateDateTime(new Date());
				//lastTransaction.setUpdateDateTime(new Date());
				lastTransaction.setStatus(TransactionStatus.ACTIVE);
			}
			else {
				lastTransaction = mostRecentTransaction;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			//ToDo:Fill this
		}
		System.out.println(lastTransaction.toString());
		return lastTransaction;
	}
	
	
	
	/*
	 * public Message addItemtoTransaction(Transaction trans, item) {
	 * 	Create new TransactionItemMapping object
	 * Set values
	 * 
	 * trans.add(mapping)
	 * 
	 * Update trans object in repo
	 * Set message based on return value
	 * 
	 * return message;
	 */
	/**public boolean addItemtoTransaction(Transaction trans, Item item){
		boolean added = false;
		try {
			TransactionItemRepository mapping = new TransactionItemRepository();
			System.out.println("ADDED");
			 added = true;
			
			//ToDo : Fill this
		}
		catch(Exception e){
			e.printStackTrace();
			//ToDo : Fill this
		}
		return added;
	}*/
	
	
	public boolean addTransaction(Transaction t) {
		TransactionRepository transRepo = new TransactionRepository();
		return transRepo.createTransaction(t);
	}
}
