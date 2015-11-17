package com.ecoRecycle.repository;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ecoRecycle.loader.HibernateLoader;
import com.ecoRecycle.model.Administrator;
import com.ecoRecycle.model.Item;
import com.ecoRecycle.model.Rcm;

public class ItemRepository {
	
	/*
	 * Given an item's id, this method return the type of the item if the item exists
	 * else it displays an error message
	 */
	
	public Item getItem(int id)
	{
    	Session session = HibernateLoader.getSessionFactory().openSession();
    	Item itemRetrived = null;
        Transaction tx = null;
       
    	try
    	{
    		tx = session.beginTransaction();
    		Query query = session.createQuery("from Item where id = :id");
    		query.setInteger("id",id);
    		
    		java.util.List allUsers = query.list();
    		if(allUsers.isEmpty())
    		{
    			System.out.println("No Items to retrive from the table");
    		}
    		
    		for (int i = 0; i < allUsers.size(); i++) 
    		{
    			itemRetrived = (Item) allUsers.get(i);
    			System.out.println("Type:" + itemRetrived.getType()); 
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
         return itemRetrived;
    }
	
	/*
	 * Retrives all the items from the database table "item"
	 * 
	 */
	
	public Item getAllItems()
	{
    	Session session = HibernateLoader.getSessionFactory().openSession();
    	Item itemsRetrived = null;
        Transaction tx = null;
       
    	try
    	{
    		tx = session.beginTransaction();
    		Query query = session.createQuery("from Item");
    		
    		java.util.List allUsers = query.list();
    		if(allUsers.isEmpty())
    		{
    			System.out.println("No Items to retrive from the table");
    		}
    		
    		for (int i = 0; i < allUsers.size(); i++) 
    		{
    			itemsRetrived = (Item) allUsers.get(i);
    			System.out.println("Type:" + itemsRetrived.getType()); 
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
         return itemsRetrived;
    }
	
	
	/*
	 * Given the type of the item, adds the item to the database's item table
	 * 
	 */
	
	public Integer addItem(String type, String isbio , String isValid, int pricePerLb)
    {
    	Session session = HibernateLoader.getSessionFactory().openSession();
        Transaction tx = null;
        Integer typeId = null;
    	try
    	{
    		tx = session.beginTransaction();
            Item item = new Item();
            item.setType(type);
            item.setBiodegradable(isbio);
            item.setIsValid(isValid);
            item.setPricePerLb(pricePerLb);
            
            typeId = (Integer) session.save(item); 
            System.out.println(typeId);
            tx.commit();
    		
    	}
    	catch(Exception e){
    		if (tx!=null) tx.rollback();
            e.printStackTrace(); 
    	}
    	finally {
            session.close(); 
         }
         return typeId;
    }
	
	// Update item
	
	public boolean removeItem(String type)
	{
    	Session session = HibernateLoader.getSessionFactory().openSession();
    	Item itemRetrived = null;
        Transaction tx = null;
        boolean isDeleted = false;
       
    	try
    	{
    		tx = session.beginTransaction();
    		Query query = session.createQuery("delete Item where type = :type");
    		query.setParameter("type",type);
    		
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
