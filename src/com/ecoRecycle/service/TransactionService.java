package com.ecoRecycle.service;


import java.util.Date;
import java.util.Observable;
import java.util.Set;

import javax.swing.JLabel;

import com.ecoRecycle.helper.Message;
import com.ecoRecycle.helper.PaymentType;
import com.ecoRecycle.helper.TransactionStatus;
import com.ecoRecycle.helper.TransactionType;
import com.ecoRecycle.model.Item;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Transaction;
import com.ecoRecycle.model.TransactionItem;
import com.ecoRecycle.repository.RcmRepository;
import com.ecoRecycle.repository.TransactionRepository;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Transaction;


public class TransactionService extends Observable{

	/*To get the last transaction of a particular rcm*/
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
			
			//check  the status of the transaction is "recycle"
			//if maxId == -1, no transaction exists for this rcm,create a new transaction
			if((maxId == -1) || 
			   (mostRecentTransaction.getStatus().equals(TransactionStatus.DONE))){
				lastTransaction = new Transaction();
				lastTransaction.setRcm(rcm);
				lastTransaction.setTotalWeight(0);
				lastTransaction.setTotalPayment(0);
				lastTransaction.setType(TransactionType.RECYCLE);
				lastTransaction.setStatus(TransactionStatus.ACTIVE);
			}
			else {
				lastTransaction = mostRecentTransaction;
			}
		}
		catch(Exception e) {
			e.printStackTrace();

		}
		
		return lastTransaction;
	}
	
	public boolean addTransaction(Transaction t) {
		TransactionRepository transRepo = new TransactionRepository();
		return transRepo.createTransaction(t);
	}
	
	/*To update the transaction if the transaction already exists*/
	public boolean updateTrasaction(Transaction trans){
		TransactionRepository transRepo = new TransactionRepository();
		return transRepo.updateTransaction(trans);
	}
	
	/*To get the last transaction that is active
	 * If the transaction is present, get the total payment to dispense to the user and upadte the rcm
	 * If not present,return message*/
	public Message dispense(Rcm rcm) {
		Message message = new Message();
		
		int maxId = -1;
		Transaction mostRecentTransaction = null;
		Set<Transaction> transactions = rcm.getTransactions();
			for(Transaction t : transactions) {
				if(t.getType().equals(TransactionType.RECYCLE) && t.getStatus() == TransactionStatus.ACTIVE) {
					if(t.getId() > maxId) {
						mostRecentTransaction = t;
						maxId = t.getId();
					}
				}
			}
			
			if(mostRecentTransaction == null) {
				message.setAmount(0);
				message.setSuccessful(true);
				return message;
			}
			
			message.setSuccessful(true);
			
			
			
			double total = 0;
			for(TransactionItem mapping: mostRecentTransaction.getTransactionItems())
				total += mapping.getPrice();
			
			mostRecentTransaction.setTotalPayment(total);
			
			if(mostRecentTransaction.getTotalPayment() <= (rcm.getTotalCashValue()-rcm.getCurrentCashValue())) {
				message.setPaymentType(PaymentType.CASH);
				rcm.setCurrentCashValue(rcm.getCurrentCashValue() + mostRecentTransaction.getTotalPayment());
							
			}
			else
				message.setPaymentType(PaymentType.COUPON);
			
			message.setAmount(mostRecentTransaction.getTotalPayment());

			
			mostRecentTransaction.setPaymentType(message.getPaymentType());
			mostRecentTransaction.setStatus(TransactionStatus.DONE);
			updateTrasaction(mostRecentTransaction);
			 new RcmRepository().updateRcm(rcm);
		
		
		return message;
	}
}
