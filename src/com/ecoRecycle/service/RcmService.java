package com.ecoRecycle.service;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ecoRecycle.loader.HibernateLoader;
import com.ecoRecycle.model.Location;
import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.repository.RcmRepository;

public class RcmService {
	
	public Integer addRcm(String rcmName, String locName, int totCapacity, int totCashValue)
	{
		
		RcmRepository rcmRepo = new RcmRepository();
    	Integer rcmId = rcmRepo.addRcm(rcmName, locName, totCapacity, totCashValue);
    	System.out.println("RCM created --- " + rcmId);
		return rcmId;
	}
	
	public boolean removeRcm(String name)
	{
    	RcmRepository rcmRepo = new RcmRepository();
    	boolean isDeleted = rcmRepo.removeRcm(name);
    	return isDeleted;
	}
	
	public ArrayList<String> getAllRcm()
	{
		ArrayList<String> rcmNames = new ArrayList<String>();
    	Session session = HibernateLoader.getSessionFactory().openSession();
    	Rcm retivedRcm = null;
        Transaction tx = null;
       
    	try
    	{
    		tx = session.beginTransaction();
    		Query query = session.createQuery("from Rcm");
    		
    		java.util.List allUsers = query.list();
    		System.out.println("Number of RCMs : " + allUsers.size());
    		if(allUsers.isEmpty())
    		{
    			System.out.println("No RCM's are present");
    		}
    		
    		for (int i = 0; i < allUsers.size(); i++) 
    		{
    			retivedRcm = (Rcm) allUsers.get(i);
    			System.out.println("Name:" + retivedRcm.getName()); 
    			rcmNames.add(retivedRcm.getName());
    		}
    		
            tx.commit();
    		
    	}
    	catch(Exception e){
    		if (tx!=null) tx.rollback();
            e.printStackTrace(); 
    	}
    	finally {
    		if (session!=null) 
            session.close(); 
         }
         return rcmNames;
    }
	
}
