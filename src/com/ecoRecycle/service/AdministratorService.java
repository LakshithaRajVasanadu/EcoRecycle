package com.ecoRecycle.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ecoRecycle.loader.HibernateLoader;
import com.ecoRecycle.model.Administrator;

public class AdministratorService {

	// Check if username and password is matching given username and password
	
	/*
	 * Given a user name and its password, this method returns the administrator if 
	 * both username and password matches 
	 * else it prints "Access Denied" and return NULL
	 */
	public Administrator userAuthentication(String userName , String password)
    {
    	Session session = HibernateLoader.getSessionFactory().openSession();
    	Administrator admin = null;
        Transaction tx = null;
       
    	try
    	{
    		tx = session.beginTransaction();
    		//Query query = session.createQuery("from Administrator where userName = :username and password = :password");
    		Query query = session.createQuery("from Administrator where userName = :username");
    		query.setParameter("username",userName);
    		//query.setParameter("password",password);
    		
    		java.util.List allUsers = query.list();
    		if(allUsers.isEmpty())
    		{
    			System.out.println("ACCESS DENIED:USER DOESNT NOT EXIST");
    		}
    		
    		
    		for (int i = 0; i < allUsers.size(); i++) 
    		{
    			admin = (Administrator) allUsers.get(i);
    			
    			System.out.println("name:" + admin.getUsername() + "Password:" + admin.getPassword()); 
				//System.out.println("Username in db : " + admin.getUsername() + " Username from UI : " + userName);

    			if(admin.getUsername().equals(userName))
    			{
    				//System.out.println("Password in db : " + admin.getPassword() + " Password from UI : " + password);
    				if(!admin.getPassword().equals(password))
    				{
    					System.out.println("USERNAME AND PASSWORD DOES NOT MATCH");
    				}
    			}
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
         return admin;
    }
	
}
