package com.ecoRecycle.service;

import com.ecoRecycle.helper.Message;
import com.ecoRecycle.helper.TransactionStatus;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.ReloadTransaction;
import com.ecoRecycle.model.Transaction;

public class ReloadTransactionService extends TransactionService{
	
	/*To create a transaction with reload type*/
	private Transaction createReloadTransaction(Rcm rcm, double money) {
		Transaction transaction = new ReloadTransaction();
		transaction.setRcm(rcm);
		transaction.setTotalPayment(money);
		transaction.setStatus(TransactionStatus.DONE);
		
		return transaction;
	}
	
	/*To reload money into the rcm */
	public Message reloadRcm(Rcm rcm, double money) {
		Message msg = new Message();
		if(rcm == null) {
			msg.setSuccessful(false);
			msg.setMessage("Rcm does not exist");
		}
		Transaction transaction = createReloadTransaction(rcm, money);
		rcm.addTransaction(transaction);
		
		rcm.setTotalCashValue(rcm.getTotalCashValue() + money);
		
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

