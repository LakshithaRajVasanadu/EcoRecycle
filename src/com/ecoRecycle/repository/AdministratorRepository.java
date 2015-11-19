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
	
	
	
}
