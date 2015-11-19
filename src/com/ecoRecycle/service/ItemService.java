package com.ecoRecycle.service;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ecoRecycle.loader.HibernateLoader;
import com.ecoRecycle.model.Item;
import com.ecoRecycle.repository.ItemRepository;
import com.ecoRecycle.repository.RcmRepository;

public class ItemService 
{
	
	public Integer addItem(String itemName, String isbio, String isValid, int pricePerLb)
	{
		
		ItemRepository itemRepo = new ItemRepository();
    	Integer itemId = itemRepo.addItem(itemName, isbio, isValid, pricePerLb);
    	System.out.println("Item created --- " + itemId);
		return itemId;
	}
	
	public boolean removeItem(String type)
	{
    	ItemRepository itemRepo = new ItemRepository();
    	boolean isDeleted = itemRepo.removeItem(type);
    	return isDeleted;
	}
	
	public ArrayList<String> getAllItems()
	{
		
			ArrayList<String> itemNames = new ArrayList<String>();
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
					itemNames.add(itemsRetrived.getType());
					 
				}
    		
				tx.commit();
    		
			}
			catch(Exception e){
    		if (tx!=null) tx.rollback();
            e.printStackTrace(); 
    	}
    	finally 
    	{
    		if (session!=null) 
            session.close(); 
        }
         return itemNames;
    }
}
