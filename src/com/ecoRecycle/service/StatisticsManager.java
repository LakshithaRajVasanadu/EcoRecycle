package com.ecoRecycle.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;

public class StatisticsManager {

	private Rmos rmos;
	
	public StatisticsManager(Rmos rmos) {
		this.rmos = rmos;
	}
	
	public Integer getItemCountByRcm(Rcm rcm, Date startDate, Date endDate) {
		
		// select count(*) as count  from transaction t join transactionItemMapping m on t.id = m.transactionId where t.rcmId = 10 and t.type = 'RECYCLE' and date(m.createDateTime) between '2015-11-22' and '2015-11-23' and m.isAccepted = true;
		return 1;
	}
	
	public HashMap<String, Integer> getItemCountForAllRcms(Date startDate, Date endDate) {
		HashMap<String, Integer> itemCounts = new HashMap<String, Integer>();
		
		//select t.rcmId as rcmId, count(*) as count  from transaction t join transactionItemMapping m on t.id = m.transactionId where t.type = 'RECYCLE' and date(m.createDateTime) between '2015-11-22' and '2015-11-23' and m.isAccepted = true group by t.rcmId;
		
		return itemCounts;
	}
	
	public List<Rcm> getMostFrequentlyUsedRcm() {
		List<Rcm> rcms = new ArrayList<Rcm>();
		// call above function
		// get max value of counts
		// get rcm id corresponding to that
		
		return rcms;
	}
	
	
	
	
}
