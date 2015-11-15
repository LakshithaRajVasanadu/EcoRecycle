package com.ecoRecycle.repository;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ecoRecycle.loader.HibernateLoader;
import com.ecoRecycle.model.Administrator;

public class AdministratorRepository {

	/*
	 * Given Username and Password, adds the user to the database's administrator table.
	 * 
	 */
	public Integer addUser(String userName , String password)
    {
    	Session session = HibernateLoader.getSessionFactory().openSession();
        Transaction tx = null;
        Integer adminId = null;
    	try
    	{
    		tx = session.beginTransaction();
            Administrator admin = new Administrator();
            admin.setUsername(userName);
            admin.setPassword(password);
            
            adminId = (Integer) session.save(admin); 
            System.out.println(adminId);
            tx.commit();
    		
    	}
    	catch(Exception e){
    		if (tx!=null) tx.rollback();
            e.printStackTrace(); 
    	}
    	finally {
            session.close(); 
         }
         return adminId;
    }
	
	/*
	 * Given a user name and its password, this method returns the administrator if 
	 * both username and password matches 
	 * else it prints "Access Denied" and return NULL
	 */
	public Administrator getUser(String userName , String password)
    {
    	Session session = HibernateLoader.getSessionFactory().openSession();
    	Administrator admin = null;
        Transaction tx = null;
       
    	try
    	{
    		tx = session.beginTransaction();
    		Query query = session.createQuery("from Administrator where userName = :username and password = :password");
    		query.setParameter("username",userName);
    		query.setParameter("password",password);
    		
    		java.util.List allUsers = query.list();
    		if(allUsers.isEmpty())
    		{
    			System.out.println("Access Denied");
    		}
    		
    		for (int i = 0; i < allUsers.size(); i++) 
    		{
    			admin = (Administrator) allUsers.get(i);
    			System.out.println("name:" + admin.getUsername() + "Password:" + admin.getPassword()); 
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
