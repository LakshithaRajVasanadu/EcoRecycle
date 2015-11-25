package com.ecoRecycle.repository;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.ecoRecycle.loader.HibernateLoader;
import com.ecoRecycle.model.Item;
import com.ecoRecycle.model.Rcm;

public class RcmRepository {
	
	public Rcm getRcmById(int id) {
		Session session = HibernateLoader.getSessionFactory().openSession();
		Transaction tx = null;
		Rcm rcm = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Rcm.class);
			criteria.add(Restrictions.eq("id", id));
			rcm = (Rcm) criteria.uniqueResult();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return rcm;
	}
	
	public Rcm getRcmByName(String name) {
		Session session = HibernateLoader.getSessionFactory().openSession();
		Transaction tx = null;
		Rcm rcm = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Rcm.class);
			criteria.add(Restrictions.eq("name", name));
			rcm = (Rcm) criteria.uniqueResult();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return rcm;
	}
	
	public boolean updateRcm(Rcm rcm){
		Session session = HibernateLoader.getSessionFactory().openSession();
		Transaction tx = null;
		boolean isSuccessful = true;
		
		try {
			tx = session.beginTransaction();
			session.update(rcm);
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
