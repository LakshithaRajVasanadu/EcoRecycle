package com.ecoRecycle.service;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ecoRecycle.loader.HibernateLoader;
import com.ecoRecycle.model.Location;

public class LocationService
{
	public ArrayList<String> getAllLocations()
	{
		ArrayList<String> cityNames = new ArrayList<String>();
    	Session session = HibernateLoader.getSessionFactory().openSession();
    	Location retivedLocations = null;
        Transaction tx = null;
       
    	try
    	{
    		tx = session.beginTransaction();
    		Query query = session.createQuery("from Location");
    		
    		java.util.List allUsers = query.list();
    		if(allUsers.isEmpty())
    		{
    			System.out.println("No Locations to retrive from the table");
    		}
    		
    		
    		
    		for (int i = 0; i < allUsers.size(); i++) 
    		{
    			retivedLocations = (Location) allUsers.get(i);
    			cityNames.add(retivedLocations.getCity()); 
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
         return cityNames;
    }
}
