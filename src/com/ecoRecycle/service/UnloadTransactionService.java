package com.ecoRecycle.service;

import java.util.Date;

import com.ecoRecycle.helper.Message;
import com.ecoRecycle.helper.TransactionStatus;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Transaction;
import com.ecoRecycle.model.UnloadTransaction;

public class UnloadTransactionService extends TransactionService{
	
	/*To create an unload transaction to empty the items from the rcm*/
	private Transaction createUnloadTransaction(Rcm rcm) {
		Transaction transaction = new UnloadTransaction();
		transaction.setRcm(rcm);
		transaction.setTotalWeight(rcm.getCurrentCapacity());
		transaction.setTotalPayment(rcm.getCurrentCashValue() + rcm.getCurrentCouponValue());
		transaction.setStatus(TransactionStatus.DONE);
		
		return transaction;
	}
	
	/*To display a message when unloaded and to update the rcm*/
	public Message unloadRcm(Rcm rcm) {
		Message msg = new Message();
		if(rcm == null) {
			msg.setSuccessful(false);
			msg.setMessage("Rcm does not exist");
		}
		Transaction transaction = createUnloadTransaction(rcm);
		rcm.addTransaction(transaction);
		
		rcm.setCurrentCapacity(0);
		rcm.setLastEmptied(new Date());
		
		RcmService rcmService = new RcmService();
		msg.setSuccessful(rcmService.updateRcm(rcm));
		
		if(!msg.isSuccessful()) {
			msg.setMessage("Could not unload items from Rcm");
		} else {
			setChanged();
			notifyObservers();
		}
		
		return msg;
	}

}
