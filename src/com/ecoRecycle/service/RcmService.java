package com.ecoRecycle.service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.ecoRecycle.helper.Message;
import com.ecoRecycle.helper.TransactionStatus;
import com.ecoRecycle.model.Item;
import com.ecoRecycle.model.Location;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.model.Transaction;
import com.ecoRecycle.model.TransactionItem;
import com.ecoRecycle.repository.RcmRepository;
import com.ecoRecycle.service.ItemManager;

public class RcmService {
	
	private RcmRepository repository = new RcmRepository();
	
	/*To get a rcm by id*/
	public Rcm getRcmById(int id) {
		Rcm rcm = repository.getRcmById(id);
		return rcm;
	}
	
	/*To get rcm by name*/
	public Rcm getRcmByName(String name) {
		Rcm rcm = repository.getRcmByName(name);
		return rcm;
	}
	
	/*To update and rcm*/
	public boolean updateRcm(Rcm rcm) {
		return repository.updateRcm(rcm);
	}
	
	/*To get a list of all the rcm's*/
	
	public Set<Rcm> getAllRcms() {
		List<Rcm> rcmList = repository.getAllRcms();
		Set<Rcm> rcmUniqueList = new HashSet<Rcm>();
		for(Rcm rcm: rcmList)
			rcmUniqueList.add(rcm);
			
		return rcmUniqueList;
	}
	
		
	/*To generate random weight of an item inserted*/
	public int randomWeightGenerator(){
		int randomNum = 0;
		try {
			Random r = new Random();
			randomNum = r.nextInt(15) + 1;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return randomNum;
	}
	
	
	/*To get the last tranaction of a particular rcm that is active
	 * To dispense the total amount of that transaction to the user
	 * To make the status of the transaction as done*/
	public double dispense(Rcm rcm)
	{
		boolean isUpdated = false;
		RcmRepository rcmRepo = new RcmRepository();
		double value = 0;
		Set<Transaction> rcmTransactions;
		try {
			if(null != rcm) {
				rcmTransactions = rcm.getTransactions();
				for(Transaction t : rcmTransactions) {
					if(null!=t.getStatus() && t.getStatus().equals(TransactionStatus.ACTIVE)){
						t.setStatus(TransactionStatus.DONE);
						isUpdated = rcmRepo.updateRcm(rcm);
						if(isUpdated) {
							value = t.getTotalPayment();
						}						
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return value;
	}
	
	
	public Message addItemToTransactionV2(String itemType, Rcm rcm, StatusManager statusManager) {
		String msg = "";
		int weightOfItem; 
		RcmRepository rcmRepo = new RcmRepository();
		TransactionService tranService = new TransactionService();
		Transaction lastTransaction = null;
		ItemManager itemManager = ItemManager.getInstance();
		Item item = null;
		double newWeight = 0;
		double newCashValue = 0;
		double itemValue = 0;
		boolean addItemSuccess = false;
		boolean isRcmUpdated = false;
		TransactionItem transItem = null;
		
		boolean isItemValid = true;
		Message message = new Message();
		
		try {
			weightOfItem = randomWeightGenerator();
			if(weightOfItem > 10) {
				msg = "Item Not accepted!";
				message.setMessage("Item not accepted");
				message.setSuccessful(false);
				isItemValid = false;
				return message;
			}
			
			if((rcm.getTotalCapacity() - rcm.getCurrentCapacity()) < weightOfItem){
				msg = "Item Not accepted due to RCM capacity reached";
				message.setMessage("Item not accepted - capacity full");
				message.setSuccessful(false);
				statusManager.deactivateRcm(rcm.getId(), "Capacity full");
				
				isItemValid = false;
				return message;
			}
				item = itemManager.getItemByType(itemType);
				lastTransaction = tranService.getLastTransaction(rcm);
				
				transItem = new TransactionItem();
				transItem.setItem(item);
				transItem.setTransaction(lastTransaction);
				transItem.setWeight(weightOfItem);
				transItem.setAccepted(true);
				transItem.setPrice(weightOfItem*item.getPricePerLb());
				
				lastTransaction.addTransactionItem(transItem);
				rcm.addTransaction(lastTransaction); 
					itemValue = weightOfItem*item.getPricePerLb();
					lastTransaction.setTotalWeight(weightOfItem + lastTransaction.getTotalWeight());
					
					newWeight = rcm.getCurrentCapacity()+weightOfItem;
						rcm.setCurrentCapacity(newWeight);
						isRcmUpdated = rcmRepo.updateRcm(rcm);
						if(isRcmUpdated) {
							msg = "Item successfully accepted by RCM";
							message.setSuccessful(true);
						}
										
					else {
						message.setSuccessful(false);
						message.setMessage("Internal error");
					}			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return message;		
	}
	
}
