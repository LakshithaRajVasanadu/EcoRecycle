package com.ecoRecycle.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import com.ecoRecycle.model.Transaction;

import com.ecoRecycle.loader.HibernateLoader;
import com.ecoRecycle.model.Item;

public class TransactionRepository {

	// Create a new transaction
	public boolean createTransaction(Transaction transaction ){
		Session session = HibernateLoader.getSessionFactory().openSession();
		org.hibernate.Transaction tx = null;
		boolean isSuccessful = true;
		
		try {
			tx = session.beginTransaction();
			session.save(transaction);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			isSuccessful = false;
			e.printStackTrace();
		} finally {
			session.close();
		}
		return isSuccessful;
	}

	
	
	// Update a transaction
	public boolean updateTransaction(Transaction transaction ){
		Session session = HibernateLoader.getSessionFactory().openSession();
		org.hibernate.Transaction tx = null;
		boolean isSuccessful = true;
		
		try {
			tx = session.beginTransaction();
			session.update(transaction);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			isSuccessful = false;
			e.printStackTrace();
		} finally {
			session.close();
		}
		return isSuccessful;
	}

	
	
	
   
}
