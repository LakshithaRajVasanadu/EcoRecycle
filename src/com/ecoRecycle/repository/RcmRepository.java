package com.ecoRecycle.repository;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ecoRecycle.loader.HibernateLoader;
import com.ecoRecycle.model.Item;
import com.ecoRecycle.model.Location;
import com.ecoRecycle.model.Rcm;

public class RcmRepository {

	// Get rcm by id
	
	// Get rcm by name
	
	// Get all rcms
	public Rcm getAllRcm()
	{
    	Session session = HibernateLoader.getSessionFactory().openSession();
    	Rcm retivedRcms = null;
        Transaction tx = null;
       
    	try
    	{
    		tx = session.beginTransaction();
    		Query query = session.createQuery("from Rcm");
    		
    		java.util.List allUsers = query.list();
    		if(allUsers.isEmpty())
    		{
    			System.out.println("No RCM's are present");
    		}
    		
    		for (int i = 0; i < allUsers.size(); i++) 
    		{
    			retivedRcms = (Rcm) allUsers.get(i);
    			System.out.println("City:" + retivedRcms.getName()); 
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
         return retivedRcms;
    }
	
	
	// Add new Rcm (with rcm items)
	public Integer addRcm(String rcmName, String locName, int totCapacity, int totCashValue)
	{
        Session session = HibernateLoader.getSessionFactory().openSession();
        Transaction tx = null;
        Integer rcmId = null;
        try{
           tx = session.beginTransaction();
           Rcm rcm = new Rcm();
           //loc.setCity(city);
           rcm.setName(rcmName);
           LocationRepository lc = new LocationRepository();
           Location location = lc.getLocationByName(locName);
           System.out.println(location);
           rcm.setLocation(location);
           rcm.setTotalCapacity(totCapacity);;
           rcm.setTotalCashValue(totCashValue);;
           
           rcmId = (Integer) session.save(rcm); 
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
        return rcmId;
     }
	
	// Update Rcm 
	
	// Remove Rcm = update rcm
	public boolean removeRcm(String name)
	{
    	Session session = HibernateLoader.getSessionFactory().openSession();
    	Rcm rcmRetrived = null;
        Transaction tx = null;
        boolean isDeleted = false;
       
    	try
    	{
    		tx = session.beginTransaction();
    		Query query = session.createQuery("delete Rcm where name = :name");
    		query.setParameter("name",name);
    		
    		/*java.util.List allUsers = query.list();
    		if(allUsers.isEmpty())
    		{
    			System.out.println("No Items to be deleted from the table");
    		}*/
    		int result = query.executeUpdate();
    		if(result == 1)
    		{
    			System.out.println("Deleted");
    			isDeleted = true;
    		}
    		
    		/*for (int i = 0; i < allUsers.size(); i++) 
    		{
    			rcmRetrived = (Rcm) allUsers.get(i);
    			System.out.println("Name:" + rcmRetrived.getName()); 
    		}*/
    		
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
         return isDeleted;
	}	
}
