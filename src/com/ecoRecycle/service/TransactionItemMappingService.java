package com.ecoRecycle.service;

import java.util.ArrayList;

import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Transaction;
import com.ecoRecycle.model.TransactionItem;
import com.ecoRecycle.repository.TransactionItemRepository;

public class TransactionItemMappingService {
	
	/*public TransactionItem getLastTranscItemMapping(Rcm rcm)
	{
		TransactionService tranService = new TransactionService();
		Transaction lastTransaction = tranService.getLastTransaction(rcm);
		int lastTransactionId = lastTransaction.getId();
		TransactionItemRepository transcItemRepo = new TransactionItemRepository();
		return transcItemRepo.getLastTransactionItemById(lastTransactionId);
		
	}/*
	
	
	/*public TransactionItem getLastTranscItemMapping(Rcm rcm)
	{
		TransactionItem mostRecentTransItem = null;
		TransactionService tranService = new TransactionService();
		Transaction lastTransaction = tranService.getLastTransaction(rcm);
		int lastTransactionId = lastTransaction.getId();
		TransactionItemRepository transcItemRepo = new TransactionItemRepository();
		ArrayList<TransactionItem> alltrans = transcItemRepo.getAllTranscationItemById(lastTransactionId);
		int maxTransItemId = -1;
		for(TransactionItem t : alltrans)
		{
			if(t.getId() > maxTransItemId)
			{
				mostRecentTransItem = t;
				maxTransItemId = t.getId();
			}
		}
		return mostRecentTransItem;
	}*/
	
	/*public ransactionItem getLastTranscItemMapping(Transaction id)
	{
		Transaction t = new Transaction();
		t.getTransactionItems();
		
	}*/
	
	public double dispense(int trancid)
	{
		int temp = trancid;
		Transaction t = new Transaction();
		double totalPayment = t.getTotalPayment();
		System.out.println(totalPayment);
		return totalPayment;
	}
	
}
