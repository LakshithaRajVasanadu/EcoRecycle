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
	
	public Integer addItem(String itemName, double pricePerLb)
	{
		
		ItemRepository itemRepo = new ItemRepository();
		Item item = new Item();
		item.setType(itemName);
		item.setIsValid(true);
		item.setPricePerLb(pricePerLb);
    	Integer itemId = itemRepo.addItem(item);
    	System.out.println("Item created --- " + itemId);
		return itemId;
	}
	
	public boolean updateItem(String type)
	{
    	ItemRepository itemRepo = new ItemRepository();
    	boolean isUpdated = itemRepo.updateItem(type);
    	return isUpdated;
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
				Query query = session.createQuery("from Item where isValid = :isValid");
				query.setParameter("isValid", true);

    		
				java.util.List allUsers = query.list();
				System.out.println(allUsers);
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
	
	public boolean changePrice(String type, int price)
	{
    	ItemRepository itemRepo = new ItemRepository();
    	boolean isUpdated = itemRepo.changeItemPrice(type, price);
    	return isUpdated;
	}
	
	public ArrayList<String> getAllItemsPrice()
	{
		
			ArrayList<String> itemNames = new ArrayList<String>();
			Session session = HibernateLoader.getSessionFactory().openSession();
			Item itemsRetrived = null;
			Transaction tx = null;
       
			try
			{
				tx = session.beginTransaction();
				Query query = session.createQuery("from Item");
				//query.setParameter("isValid", "valid");

    		
				java.util.List allUsers = query.list();
				System.out.println(allUsers);
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
