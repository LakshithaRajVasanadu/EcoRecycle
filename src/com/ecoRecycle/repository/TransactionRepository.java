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
import com.ecoRecycle.helper.TransactionType;
import com.ecoRecycle.loader.HibernateLoader;
import com.ecoRecycle.model.Item;

public class TransactionRepository {

	/*reate a new transaction when the first item is inserted*/
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

	
	// Get item count by RCM
	public Integer getItemCountByRcm(int rcmId, Date startDate, Date endDate) {

		/*
		 * select count(*) as count from transaction t join
		 * transactionItemMapping m on t.id = m.transactionId where t.rcmId = 10
		 * and t.type = 'RECYCLE' and date(m.createDateTime) between
		 * '2015-11-22' and '2015-11-23' and m.isAccepted = true;
		 */

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

		System.out.println("Count is:" + count);

		return count;
	}
	
	
	public double getItemWeightByRcm(int rcmId, Date startDate, Date endDate) {
		/*
		 * select sum(totalWeight) as weight from transaction t where t.rcmId = 10 and t.type = 'RECYCLE' and date(t.createDateTime) between "2015-11-22" and "2015-11-25" ;
		 */
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

		System.out.println("Weight is:" + weight);

		return weight;
	}
	
	public double getTotalValueDispensed(int rcmId, Date startDate, Date endDate) {
		/*
		 * select sum(totalPayment) as value from transaction t where t.rcmId = 10 and t.type = 'RECYCLE' and date(t.createDateTime) between "2015-11-22" and "2015-11-25" ;
		 */
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

		System.out.println("Value is:" + totalValueDispensed);

		return totalValueDispensed;
	}
	
	public Integer getNumberofTimesEmptied(int rcmId, Date startDate, Date endDate) {
		/*
		 * select count(*) as count from transaction t where t.rcmId = 10 and t.type = 'UNLOAD' and date(t.createDateTime) between "2015-11-22" and "2015-11-25" ;
		 */
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

		System.out.println("Empty times is:" + count);

		return count;
	}
	/*
	public static void main(String[] args) {
		TransactionRepository repo  = new TransactionRepository();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = "2015-11-22";
		String endDate = "2015-11-25";
		Date sdate = null, edate = null;
		try {

			sdate = formatter.parse(startDate);
			edate = formatter.parse(endDate);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		repo.getItemCountByRcm(10, sdate, edate);
		
		repo.getItemWeightByRcm(10, sdate, edate);
		
		repo.getTotalValueDispensed(10, sdate, edate);
		
		repo.getNumberofTimesEmptied(10, sdate, edate);
	}
	*/
	
   
}
