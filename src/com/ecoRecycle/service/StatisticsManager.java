package com.ecoRecycle.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.ecoRecycle.helper.UsageStatisticsModel;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.repository.RcmRepository;
import com.ecoRecycle.repository.RmosRepository;
import com.ecoRecycle.repository.TransactionRepository;

public class StatisticsManager {

	private Rmos rmos;
	private TransactionRepository transactionRepository = new TransactionRepository();
	
	public StatisticsManager(Rmos rmos) {
		this.rmos = rmos;
	}
	
	/*Gets the number of items recyled by a particular rcm based on particular period of time*/
	public Integer getItemCountByRcm(Rcm rcm, Date startDate, Date endDate) {
		 return transactionRepository.getItemCountByRcm(rcm.getId(), startDate, endDate);
	}
	
	/*Gets the count of items recycled by all the rcm's based on a particular period of time*/
	public HashMap<Rcm, Integer> getItemCountForAllRcms(Date startDate, Date endDate) {
		
		HashMap<Rcm, Integer> itemCountsMap = new HashMap<Rcm, Integer>();
		
		RmosManager rmosManager = new RmosManager(rmos);
		List<Rcm> rcms = rmosManager.getAllRcms();
		
		for(Rcm rcm: rcms) 
			itemCountsMap.put(rcm, getItemCountByRcm(rcm, startDate, endDate));
				
		return itemCountsMap;
	}
	
	/*TO get the most frequently used rcm in a partiicular date range*/
	public List<Entry<Rcm, Integer>> getMostFrequentlyUsedRcm(Date startDate, Date endDate) {
		HashMap<Rcm, Integer> itemCountsMap = getItemCountForAllRcms(startDate, endDate);
		Integer largestVal = null;
		List<Entry<Rcm, Integer>> largestList = new ArrayList<Entry<Rcm, Integer>>();
		for (Entry<Rcm, Integer> i : itemCountsMap.entrySet()){
		     if (largestVal == null || largestVal  < i.getValue()){
		         largestVal = i.getValue();
		         largestList .clear();
		         largestList .add(i);
		     }else if (largestVal == i.getValue()){
		         largestList .add(i);
		     }
		}
		
		return largestList;
	}
	
	/*To get the timestamp of the rcm being last emptied*/
	public Date getLastEmptied(Rcm rcm) {
		return rcm.getLastEmptied();
	}
	
	/*To get the usage statistics of all the rcm's*/
	public HashMap<Rcm, UsageStatisticsModel> getUsageStatistics(Date startDate, Date endDate) {
		HashMap<Rcm, UsageStatisticsModel> statisticsMap = new HashMap<Rcm, UsageStatisticsModel>();
		
		RmosManager rmosManager = new RmosManager(rmos);
		List<Rcm> rcms = rmosManager.getAllRcms();
		
		for(Rcm rcm: rcms) {
			UsageStatisticsModel statisticsModel = new UsageStatisticsModel();
			
			statisticsModel.setTotalWeight(transactionRepository.getItemWeightByRcm(rcm.getId(), startDate, endDate));
			statisticsModel.setTotalValue(transactionRepository.getTotalValueDispensed(rcm.getId(), startDate, endDate));
			statisticsModel.setNumberOfTimesEmptied(transactionRepository.getNumberofTimesEmptied(rcm.getId(), startDate, endDate));
			statisticsModel.setNumberOfItems(transactionRepository.getItemCountByRcm(rcm.getId(), startDate, endDate));
			
			statisticsMap.put(rcm, statisticsModel);
		}
		
		return statisticsMap;
	}
	
}