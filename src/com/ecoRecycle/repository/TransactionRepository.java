package com.ecoRecycle.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Transaction;
import com.ecoRecycle.helper.HibernateLoader;
import com.ecoRecycle.helper.TransactionType;
import com.ecoRecycle.model.Item;

public class TransactionRepository {

	/*create a new transaction when the first item is inserted*/
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

	
	
	/*To update a transaction if transaction alredy exists*/
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

	
	/*To get the number of items recycled by a particular rcm*/
	public Integer getItemCountByRcm(int rcmId, Date startDate, Date endDate) {
		Session session = HibernateLoader.getSessionFactory().openSession();
		org.hibernate.Transaction tx = null;
		Integer count = 0;

		try {

			tx = session.beginTransaction();
			String hql = "SELECT count(*) as count "
					+ "FROM Transaction t "
					+ "JOIN t.transactionItems m "
					+ "WHERE t.rcm.id = :rcmId and t.type = :type "
					+ "and date(m.createDateTime) between :startDate and :endDate "
					+ "and m.isAccepted = true";
			Query query = session.createQuery(hql);
			query.setParameter("rcmId", rcmId);
			query.setParameter("type", TransactionType.RECYCLE);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			List results = query.list();
			count = Integer.parseInt(results.get(0).toString());

			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return count;
	}
	
	/*To get the total weight of items recycled by a particular rcm*/
	public double getItemWeightByRcm(int rcmId, Date startDate, Date endDate) {
		
		Session session = HibernateLoader.getSessionFactory().openSession();
		org.hibernate.Transaction tx = null;
		double weight = 0;
		
		try {

			tx = session.beginTransaction();
			String hql = "SELECT sum(totalWeight) as weight "
						+ "FROM Transaction t "
						+ "WHERE t.rcm.id = :rcmId and t.type = :type "
						+ "and date(t.createDateTime) between :startDate and :endDate ";
			Query query = session.createQuery(hql);
			query.setParameter("rcmId", rcmId);
			query.setParameter("type", TransactionType.RECYCLE);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			List results = query.list();
			if(results != null && results.get(0) != null)
				weight = Double.parseDouble(results.get(0).toString());

			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return weight;
	}
	
	/*To get the total value to be dispensed to the user at the end of a transaction*/
	public double getTotalValueDispensed(int rcmId, Date startDate, Date endDate) {
		Session session = HibernateLoader.getSessionFactory().openSession();
		org.hibernate.Transaction tx = null;
		double totalValueDispensed = 0;
		
		try {

			tx = session.beginTransaction();
			String hql = "SELECT sum(totalPayment) as value "
						+ "FROM Transaction t "
						+ "WHERE t.rcm.id = :rcmId and t.type = :type "
						+ "and date(t.createDateTime) between :startDate and :endDate ";
			Query query = session.createQuery(hql);
			query.setParameter("rcmId", rcmId);
			query.setParameter("type", TransactionType.RECYCLE);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			List results = query.list();
			
			if(results != null && results.get(0) != null)
				totalValueDispensed = Double.parseDouble(results.get(0).toString());

			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return totalValueDispensed;
	}
	
	/*To get the number of times a particular rcm was emptied*/
	public Integer getNumberofTimesEmptied(int rcmId, Date startDate, Date endDate) {
		Session session = HibernateLoader.getSessionFactory().openSession();
		org.hibernate.Transaction tx = null;
		Integer count = 0;
		
		try {

			tx = session.beginTransaction();
			String hql = "SELECT count(*) as count "
						+ "FROM Transaction t "
						+ "WHERE t.rcm.id = :rcmId and t.type = :type "
						+ "and date(t.createDateTime) between :startDate and :endDate ";
			Query query = session.createQuery(hql);
			query.setParameter("rcmId", rcmId);
			query.setParameter("type", TransactionType.UNLOAD);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			List results = query.list();
			count = Integer.parseInt(results.get(0).toString());

			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return count;
	}
	
}
