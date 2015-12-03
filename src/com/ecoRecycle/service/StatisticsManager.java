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
	
	public Integer getItemCountByRcm(Rcm rcm, Date startDate, Date endDate) {
		 return transactionRepository.getItemCountByRcm(rcm.getId(), startDate, endDate);
	}
	
	public HashMap<Rcm, Integer> getItemCountForAllRcms(Date startDate, Date endDate) {
		//select t.rcmId as rcmId, count(*) as count  from transaction t join transactionItemMapping m on t.id = m.transactionId where t.type = 'RECYCLE' and date(m.createDateTime) between '2015-11-22' and '2015-11-23' and m.isAccepted = true group by t.rcmId;

		HashMap<Rcm, Integer> itemCountsMap = new HashMap<Rcm, Integer>();
		
		RmosManager rmosManager = new RmosManager(rmos);
		List<Rcm> rcms = rmosManager.getAllRcms();
		
		for(Rcm rcm: rcms) 
			itemCountsMap.put(rcm, getItemCountByRcm(rcm, startDate, endDate));
				
		return itemCountsMap;
	}
	
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
	
	public Date getLastEmptied(Rcm rcm) {
		return rcm.getLastEmptied();
	}
	
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
	
	/*public static void main(String[] args) {
		StatisticsManager mgr  = new StatisticsManager(new RmosRepository().getRmosById(3));
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = "2015-11-22";
		String endDate = "2015-11-25";
		Date sdate = null, edate = null;
		try {

			sdate = formatter.parse(startDate);
			edate = formatter.parse(endDate);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int count = mgr.getItemCountByRcm(new RcmRepository().getRcmById(10), sdate, edate);
		System.out.println("Rcm 10 - item count:" + count);
		
		HashMap<Rcm, Integer> countMap = mgr.getItemCountForAllRcms(sdate, edate);
		for (Entry<Rcm, Integer> i : countMap.entrySet()){
			System.out.println("---Rcm id:" + i.getKey().getId() + " Count:" + i.getValue());
		}
		
		List<Entry<Rcm, Integer>> list = mgr.getMostFrequentlyUsedRcm(sdate, edate);
		for (Entry<Rcm, Integer> temp : list) {
			System.out.println("--MFU--Rcm id:" + temp.getKey().getId() + " Count:" + temp.getValue());
			 
		}
		
		HashMap<Rcm, UsageStatisticsModel> sModel = mgr.getUsageStatistics(sdate, edate);
		for (Entry<Rcm, UsageStatisticsModel> i : sModel.entrySet()){
			System.out.println("-Usage--Rcm id:" + i.getKey().getId());
			System.out.println("Stats:" + i.getValue());
		}
		
//		repo.getItemWeightByRcm(10, sdate, edate);
//		
//		repo.getTotalValueDispensed(10, sdate, edate);
//		
//		repo.getNumberofTimesEmptied(10, sdate, edate);
	}
	
	
	*/
}
