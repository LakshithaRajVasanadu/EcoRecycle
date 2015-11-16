package com.ecoRecycle.repository;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ecoRecycle.loader.HibernateLoader;
import com.ecoRecycle.model.Item;
import com.ecoRecycle.model.Location;

public class LocationRepository {

	// Get location by id
	public Location getLocation(int id)
	{
    	Session session = HibernateLoader.getSessionFactory().openSession();
    	Location retriveLocation = null;
        Transaction tx = null;
       
    	try
    	{
    		tx = session.beginTransaction();
    		Query query = session.createQuery("from Location where id = :id");
    		query.setInteger("id",id);
    		
    		java.util.List allUsers = query.list();
    		if(allUsers.isEmpty())
    		{
    			System.out.println("There are no locations in the table");
    		}
    		
    		for (int i = 0; i < allUsers.size(); i++) 
    		{
    			retriveLocation = (Location)allUsers.get(i);
    			System.out.println("City:" + retriveLocation.getCity()); 
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
         return retriveLocation;
    }
	
	//Get location by name
	public Location getLocationByName(String city)
	{
    	Session session = HibernateLoader.getSessionFactory().openSession();
    	Location retriveLocationName = null;
        Transaction tx = null;
       
    	try
    	{
    		tx = session.beginTransaction();
    		Query query = session.createQuery("from Location where city = :city");
    		query.setParameter("city",city);
    		
    		java.util.List allUsers = query.list();
    		if(allUsers.isEmpty())
    		{
    			System.out.println("There are no locations in the table");
    		}
    		
    		for (int i = 0; i < allUsers.size(); i++) 
    		{
    			retriveLocationName = (Location)allUsers.get(i);
    			System.out.println("City:" + retriveLocationName.getCity()); 
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
         return retriveLocationName;
    }
	
	// Get all locations
	
	public Location getAllLocations()
	{
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
    			System.out.println("City:" + retivedLocations.getCity()); 
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
         return retivedLocations;
    }
	
	
	// Add new location
	public Integer addLocation(String city){
        Session session = HibernateLoader.getSessionFactory().openSession();
        Transaction tx = null;
        Integer locId = null;
        try{
           tx = session.beginTransaction();
           Location loc = new Location();
           loc.setCity(city);
           
           locId = (Integer) session.save(loc); 
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
        return locId;
     }
	
	// Update a location
}
