package com.ecoRecycle.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.ecoRecycle.helper.HibernateLoader;
import com.ecoRecycle.helper.RcmStatus;
import com.ecoRecycle.model.Item;
import com.ecoRecycle.model.Location;
import com.ecoRecycle.model.Rcm;

public class RcmRepository {
	
	/*To get a rcm by Id*/
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
	
	/*To get rcm by name*/
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
	
	/*To update the rcm when the values in the rcm has been changed*/
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
	
	/*To get all the rcms that are active and inactive*/
	public List<Rcm> getAllRcms() {
		Session session = HibernateLoader.getSessionFactory().openSession();
		Transaction tx = null;
		List<Rcm> rcms = null;
		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Rcm.class);
			criteria.add(Restrictions.ne("status", RcmStatus.REMOVED));
			rcms = criteria.list();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return rcms;
	} 
}
