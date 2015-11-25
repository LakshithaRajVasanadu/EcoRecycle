package com.ecoRecycle.service;

import java.util.Date;

import com.ecoRecycle.helper.Message;
import com.ecoRecycle.helper.TransactionStatus;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Transaction;
import com.ecoRecycle.model.UnloadTransaction;

public class UnloadTransactionService extends TransactionService{
	
	private Transaction createUnloadTransaction(Rcm rcm) {
		Transaction transaction = new UnloadTransaction();
		transaction.setRcm(rcm);
		transaction.setTotalWeight(rcm.getCurrentCapacity());
		transaction.setTotalPayment(rcm.getCurrentCashValue() + rcm.getCurrentCouponValue());
		transaction.setStatus(TransactionStatus.DONE);
		
		return transaction;
	}
	
	public Message unloadRcm(Rcm rcm) {
		Message msg = new Message();
		if(rcm == null) {
			msg.setSuccessful(false);
			msg.setMessage("Rcm does not exist");
		}
		Transaction transaction = createUnloadTransaction(rcm);
		rcm.addTransaction(transaction);
		
		rcm.setCurrentCapacity(0);
		rcm.setCurrentCashValue(0);
		rcm.setCurrentCouponValue(0);
		rcm.setLastEmptied(new Date());
		
		RcmService rcmService = new RcmService();
		msg.setSuccessful(rcmService.updateRcm(rcm));
		
		if(!msg.isSuccessful()) {
			msg.setMessage("Could not unload items from Rcm");
		}
		
		return msg;
	}

}
